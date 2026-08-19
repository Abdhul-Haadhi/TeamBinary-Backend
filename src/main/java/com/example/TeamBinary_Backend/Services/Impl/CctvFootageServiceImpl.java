package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CctvFootageDto;
import com.example.TeamBinary_Backend.DTOs.CctvVideoProcessingResponseDto;
import com.example.TeamBinary_Backend.Entities.CctvCameraEntity;
import com.example.TeamBinary_Backend.Entities.CctvFootageEntity;
import com.example.TeamBinary_Backend.Entities.CctvVehicleDetectionEntity;
import com.example.TeamBinary_Backend.Repositories.CctvCameraRepository;
import com.example.TeamBinary_Backend.Repositories.CctvFootageRepository;
import com.example.TeamBinary_Backend.Repositories.CctvVehicleDetectionRepository;
import com.example.TeamBinary_Backend.Services.CctvFootageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CctvFootageServiceImpl implements CctvFootageService {

    private final CctvFootageRepository footageRepository;
    private final CctvCameraRepository cameraRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CctvVehicleDetectionRepository vehicleDetectionRepository;

    @Value("${cctv.storage.location}")
    private String storageLocation;

    public CctvFootageServiceImpl(CctvFootageRepository footageRepository, CctvCameraRepository cameraRepository, RestTemplate restTemplate, ObjectMapper objectMapper, CctvVehicleDetectionRepository vehicleDetectionRepository) {
        this.footageRepository = footageRepository;
        this.cameraRepository = cameraRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.vehicleDetectionRepository = vehicleDetectionRepository;
    }

    @Override
    public CctvFootageDto uploadFootage(Long cameraId, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Video file is empty.");
        }



        CctvCameraEntity camera = cameraRepository.findById(cameraId).orElseThrow(
                                () -> new RuntimeException(
                                        "Camera not found: "
                                                + cameraId
                                )
                        );


        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("video/")) {

            throw new RuntimeException("Only video files are allowed.");
        }


        try {

            Path cameraDirectory =
                    Paths.get(storageLocation, String.valueOf(cameraId));


            Files.createDirectories(cameraDirectory);


            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());



            String extension = getFileExtension(originalFileName
            );


            String storedFileName = UUID.randomUUID() + extension;


            Path targetPath = cameraDirectory.resolve(storedFileName);


            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            CctvFootageEntity footage = new CctvFootageEntity();

            footage.setCamera(camera);
            footage.setOriginalFileName(originalFileName);
            footage.setStoredFileName(storedFileName);
            footage.setFilePath(targetPath.toString());
            footage.setFileSize(file.getSize());
            footage.setUploadedAt(LocalDateTime.now());
            footage.setProcessingStatus("UPLOADED");

            CctvFootageEntity saved = footageRepository.save(footage);

            return convertToDto(saved);


        } catch (IOException e) {
            throw new RuntimeException("Could not save CCTV footage: " + e.getMessage(), e);
        }
    }


    @Override
    public List<CctvFootageDto> getFootageByCamera(Long cameraId) {

        List<CctvFootageEntity> footageList = footageRepository.findByCameraIdOrderByUploadedAtDesc(cameraId);


        List<CctvFootageDto> result = new ArrayList<>();


        for (CctvFootageEntity footage : footageList) {

            result.add(convertToDto(footage));
        }

        return result;
    }


    @Override
    public void deleteFootage(Long footageId) {

        CctvFootageEntity footage = footageRepository.findById(footageId).orElseThrow(() -> new RuntimeException("Footage not found: " + footageId));

        try {

            Path filePath = Paths.get(footage.getFilePath());


            Files.deleteIfExists(filePath);


        } catch (IOException e) {
            throw new RuntimeException("Could not delete video file.", e);
        }

        footageRepository.delete(
                footage
        );
    }


    private String getFileExtension(String fileName) {

        int index = fileName.lastIndexOf('.');


        if (index == -1) {
            return ".mp4";
        }


        return fileName.substring(index);
    }

    @Override
    @Transactional
    public CctvVideoProcessingResponseDto processFootage(Long footageId) {

        CctvFootageEntity footage = footageRepository.findById(footageId).orElseThrow(() -> new RuntimeException("Footage not found: " + footageId));

        try {

            footage.setProcessingStatus("PROCESSING");

            footageRepository.save(footage);


            Path videoPath = Paths.get(footage.getFilePath());


            if (!Files.exists(videoPath)) {
                throw new RuntimeException("Video file does not exist: " + videoPath);
            }


            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(videoPath)) {

                        @Override
                        public String getFilename() {
                            return footage.getOriginalFileName();
                        }
                    };


            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            body.add("file", resource);


            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.MULTIPART_FORM_DATA);


            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);


            ResponseEntity<String> response = restTemplate.postForEntity(

                            "http://127.0.0.1:8000/process-video",

                            request,

                            String.class
                    );


            CctvVideoProcessingResponseDto processingResponse = objectMapper.readValue(response.getBody(), CctvVideoProcessingResponseDto.class);


            if (processingResponse.getDetections() != null) {
                List<CctvVideoProcessingResponseDto.DetectionDto> detections = processingResponse.getDetections();

                for (CctvVideoProcessingResponseDto.DetectionDto detection : detections) {


                    if (detection.getDetectedPlate() == null || detection.getDetectedPlate().isBlank()) {
                        continue;
                    }


                    CctvVehicleDetectionEntity entity = new CctvVehicleDetectionEntity();


                    entity.setFootage(footage);
                    entity.setFrameNumber(detection.getFrameNumber());
                    entity.setTimestampSeconds(detection.getTimestampSeconds());
                    entity.setTimestamp(detection.getTimestamp());
                    entity.setDetectedPlate(detection.getDetectedPlate());
                    entity.setPlateConfidence(detection.getPlateConfidence());
                    entity.setOcrConfidence(detection.getOcrConfidence());
                    entity.setX1(detection.getX1());
                    entity.setY1(detection.getY1());
                    entity.setX2(detection.getX2());


                    entity.setY2(detection.getY2());


                    vehicleDetectionRepository.save(entity);
                }
            }

            footage.setProcessingStatus("COMPLETED");


            footage.setProcessingCompletedAt(LocalDateTime.now());


            footageRepository.save(footage);


            return processingResponse;


        } catch (Exception e) {

            footage.setProcessingStatus("FAILED");

            footageRepository.save(footage);

            throw new RuntimeException("CCTV processing failed: " + e.getMessage(), e);
        }
    }


    private CctvFootageDto convertToDto(CctvFootageEntity footage) {

        CctvFootageDto dto = new CctvFootageDto();

        dto.setId(footage.getId());
        dto.setCameraId(footage.getCamera().getId());
        dto.setFileName(footage.getOriginalFileName());
        dto.setFilePath(footage.getFilePath());
        dto.setFileSize(footage.getFileSize());
        dto.setUploadedAt(footage.getUploadedAt());
        dto.setProcessingStatus(footage.getProcessingStatus());
        dto.setProcessingCompletedAt(footage.getProcessingCompletedAt());

        return dto;
    }

}
