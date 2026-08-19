package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.DetectionResponseDto;
import com.example.TeamBinary_Backend.DTOs.VehicleSearchResponseDto;
import com.example.TeamBinary_Backend.DTOs.VehicleSearchResultDto;

import com.example.TeamBinary_Backend.Entities.CctvCameraEntity;
import com.example.TeamBinary_Backend.Entities.CctvFootageEntity;
import com.example.TeamBinary_Backend.Entities.CctvVehicleDetectionEntity;
import com.example.TeamBinary_Backend.Entities.VehicleSearchResultEntity;
import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;

import com.example.TeamBinary_Backend.Repositories.CctvVehicleDetectionRepository;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchResultRepository;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchSessionRepository;

import com.example.TeamBinary_Backend.Services.VehicleSearchService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleSearchServiceImpl implements VehicleSearchService {

    private final VehicleSearchSessionRepository sessionRepository;
    private final VehicleSearchResultRepository resultRepository;
    private final CctvVehicleDetectionRepository cctvDetectionRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String AI_SERVICE_URL = "http://127.0.0.1:8000/detect";

    public VehicleSearchServiceImpl(VehicleSearchSessionRepository sessionRepository,
            VehicleSearchResultRepository resultRepository,
            CctvVehicleDetectionRepository cctvDetectionRepository,
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    ) {
        this.sessionRepository = sessionRepository;
        this.resultRepository = resultRepository;
        this.cctvDetectionRepository = cctvDetectionRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    @Transactional
    public VehicleSearchResponseDto searchVehicle(MultipartFile file
    ) {

        VehicleSearchSessionEntity session = new VehicleSearchSessionEntity();

        session.setOriginalFileName(file.getOriginalFilename());
        session.setSearchedAt(LocalDateTime.now());
        session.setStatus("SEARCHING");
        session = sessionRepository.save(session);


        try {

            DetectionResponseDto aiResponse = detectQueryVehicle(file);


            DetectionResponseDto.DetectionDto bestDetection = findBestPlateDetection(aiResponse);

            if (bestDetection == null || bestDetection.getRecognizedText() == null || bestDetection.getRecognizedText().isBlank()) {

                session.setStatus("NO_PLATE_DETECTED");

                sessionRepository.save(session);

                return buildResponse(session, file.getOriginalFilename(), null, 0.0, new ArrayList<>());
            }


            String queryPlate = normalizePlate(bestDetection.getRecognizedText());


            double queryOcrConfidence = bestDetection.getOcrConfidence();


            System.out.println("======================================");
            System.out.println("VEHICLE SEARCH");
            System.out.println("Query plate: " + queryPlate);
            System.out.println("OCR confidence: " + queryOcrConfidence);
            System.out.println("======================================");


            List<CctvVehicleDetectionEntity> matches = cctvDetectionRepository.findByDetectedPlateIgnoreCase(queryPlate);

            System.out.println("CCTV matches found: " + matches.size());

            List<CctvVehicleDetectionEntity> groupedMatches = groupNearbyDetections(matches);

            List<VehicleSearchResultEntity> savedResults = new ArrayList<>();


            for (CctvVehicleDetectionEntity detection : groupedMatches) {

                CctvFootageEntity footage = detection.getFootage();
                CctvCameraEntity camera = footage.getCamera();

                double plateConfidence = safeValue(detection.getPlateConfidence());

                double ocrConfidence = safeValue(detection.getOcrConfidence());

                double matchConfidence = (plateConfidence + ocrConfidence) / 2.0;

                VehicleSearchResultEntity result = new VehicleSearchResultEntity();

                result.setSearchSession(session);
                result.setDetection(detection);
                result.setCameraId(camera.getId());
                result.setCameraName(camera.getCameraName());
                result.setCameraLocation(camera.getLocation());
                result.setVideoFileName(footage.getOriginalFileName());
                result.setVideoUrl(footage.getFilePath());
                result.setDetectedPlate(detection.getDetectedPlate());
                result.setTimestamp(detection.getTimestamp());
                result.setMatchConfidence(round(matchConfidence, 4));

                VehicleSearchResultEntity savedResult = resultRepository.save(result);

                savedResults.add(savedResult);
            }


            if (savedResults.isEmpty()) {
                session.setStatus("NO_MATCHES");
            } else {
                session.setStatus("COMPLETED");
            }

            sessionRepository.save(session);

            List<VehicleSearchResultDto> resultDtos = new ArrayList<>();


            for (VehicleSearchResultEntity result : savedResults) {
                resultDtos.add(convertToDto(result));
            }

            return buildResponse(session, file.getOriginalFilename(), queryPlate, queryOcrConfidence, resultDtos);


        } catch (Exception e) {

            e.printStackTrace();


            session.setStatus("FAILED");

            sessionRepository.save(session);

            throw new RuntimeException("Vehicle search failed: " + e.getMessage(), e);
        }
    }

    private DetectionResponseDto detectQueryVehicle(MultipartFile file) throws Exception {

        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {

                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };


        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(AI_SERVICE_URL, request, String.class);

        return objectMapper.readValue(response.getBody(), DetectionResponseDto.class);
    }

    private DetectionResponseDto.DetectionDto findBestPlateDetection(DetectionResponseDto response) {

        if (response == null || response.getDetections() == null || response.getDetections().isEmpty()) {
            return null;
        }


        return response.getDetections().stream()
                .filter(detection -> detection.getRecognizedText() != null && !detection.getRecognizedText().isBlank()).max(Comparator.comparingDouble(DetectionResponseDto.DetectionDto::getOcrConfidence)).orElse(null);
    }

    private String normalizePlate(String plate) {

        if (plate == null) {
            return "";
        }
        return plate.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }


    private List<CctvVehicleDetectionEntity> groupNearbyDetections(List<CctvVehicleDetectionEntity> matches) {
        if (matches == null || matches.isEmpty()) {
            return new ArrayList<>();
        }

        matches.sort(Comparator.comparing((CctvVehicleDetectionEntity d) -> d.getFootage().getCamera().getId()).thenComparing(d -> d.getFootage().getId())
                        .thenComparing(CctvVehicleDetectionEntity::getTimestampSeconds)
        );

        List<CctvVehicleDetectionEntity> grouped = new ArrayList<>();


        CctvVehicleDetectionEntity previous = null;

        for (CctvVehicleDetectionEntity current : matches) {
            if (previous == null) {
                grouped.add(current);
                previous = current;
                continue;
            }
            boolean sameFootage = previous
                            .getFootage()
                            .getId()
                            .equals(current.getFootage().getId());


            double previousTime = safeValue(previous.getTimestampSeconds());

            double currentTime = safeValue(current.getTimestampSeconds());

            double difference = currentTime - previousTime;


            if (sameFootage && difference <= 5.0) {

                double previousScore = (safeValue(previous.getPlateConfidence()) + safeValue(previous.getOcrConfidence())) / 2.0;

                double currentScore = (safeValue(current.getPlateConfidence()) + safeValue(current.getOcrConfidence())) / 2.0;

                if (currentScore > previousScore) {
                    grouped.set(grouped.size() - 1, current);
                    previous = current;
                }

            } else {
                grouped.add(current);
                previous = current;
            }
        }
        return grouped;
    }


    private VehicleSearchResultDto convertToDto(VehicleSearchResultEntity entity) {

        VehicleSearchResultDto dto = new VehicleSearchResultDto();

        dto.setId(entity.getId());

        if (entity.getDetection() != null) {

            dto.setDetectionId(entity.getDetection().getId());
            dto.setPlateConfidence(entity.getDetection().getPlateConfidence());

            dto.setOcrConfidence(entity.getDetection().getOcrConfidence());
        }

        dto.setCameraId(entity.getCameraId());
        dto.setCameraName(entity.getCameraName());
        dto.setCameraLocation(entity.getCameraLocation());
        dto.setVideoFileName(entity.getVideoFileName());
        dto.setVideoUrl(entity.getVideoUrl());
        dto.setMatchConfidence(entity.getMatchConfidence());
        dto.setDetectedPlate(entity.getDetectedPlate());
        dto.setDetectedVehicleType(entity.getDetectedVehicleType());
        dto.setDetectedColor(entity.getDetectedColor());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }


    private VehicleSearchResponseDto
    buildResponse(VehicleSearchSessionEntity session, String filename, String detectedPlate, Double queryOcrConfidence, List<VehicleSearchResultDto> results) {
        VehicleSearchResponseDto response = new VehicleSearchResponseDto();

        response.setSuccess(true);
        response.setSearchSessionId(session.getId());
        response.setFilename(filename);
        response.setDetectedPlate(detectedPlate);
        response.setQueryOcrConfidence(queryOcrConfidence);
        response.setStatus(session.getStatus());
        response.setResults(results);

        return response;
    }


    private double safeValue(Double value) {

        if (value == null) {
            return 0.0;
        }

        return value;
    }


    private double round(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);

        return Math.round(value * factor) / factor;
    }
}