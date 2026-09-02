package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.DetectionResponseDto;
import com.example.TeamBinary_Backend.Entities.DetectionSessionEntity;
import com.example.TeamBinary_Backend.Entities.PlateDetectionEntity;
import com.example.TeamBinary_Backend.Repositories.DetectionSessionRepository;
import com.example.TeamBinary_Backend.Services.NumberPlateService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberPlateServiceImpl implements NumberPlateService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final DetectionSessionRepository detectionSessionRepository;

    private final String AI_SERVICE_URL =
            "http://127.0.0.1:8000/detect";


    public NumberPlateServiceImpl(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            DetectionSessionRepository detectionSessionRepository
    ) {

        this.restTemplate = restTemplate;

        this.objectMapper = objectMapper;

        this.detectionSessionRepository =
                detectionSessionRepository;
    }


    @Override
    public DetectionResponseDto detectNumberPlate(MultipartFile file) {

        try {

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


            System.out.println("======================================");

            System.out.println("Python AI Response:");

            System.out.println(response.getBody());

            System.out.println("======================================");

            DetectionResponseDto detectionResponse = objectMapper.readValue(response.getBody(), DetectionResponseDto.class);

            DetectionSessionEntity session = new DetectionSessionEntity();

            session.setOriginalFileName(file.getOriginalFilename());

            session.setDetectedAt(LocalDateTime.now());

            List<DetectionResponseDto.DetectionDto> detections = detectionResponse.getDetections();


            if (detections == null) {
                detections = new ArrayList<>();
            }


            session.setTotalDetections(
                    detections.size()
            );


            // =====================================================
            // STEP 5
            // Create database detection records
            // =====================================================

            List<PlateDetectionEntity>
                    plateDetectionEntities =
                    new ArrayList<>();


            for (
                    DetectionResponseDto.DetectionDto detection
                    : detections
            ) {

                PlateDetectionEntity
                        plateDetection =
                        new PlateDetectionEntity();


                // -------------------------------------------------
                // YOLO information
                // -------------------------------------------------

                plateDetection.setClassName(
                        detection.getClassName()
                );


                plateDetection.setConfidence(
                        detection.getConfidence()
                );


                // -------------------------------------------------
                // Bounding box
                // -------------------------------------------------

                plateDetection.setX1(
                        detection.getX1()
                );


                plateDetection.setY1(
                        detection.getY1()
                );


                plateDetection.setX2(
                        detection.getX2()
                );


                plateDetection.setY2(
                        detection.getY2()
                );


                // -------------------------------------------------
                // OCR information
                // -------------------------------------------------

                plateDetection.setRecognizedText(
                        detection.getRecognizedText()
                );


                plateDetection.setOcrConfidence(
                        detection.getOcrConfidence()
                );


                // -------------------------------------------------
                // Connect detection to session
                // -------------------------------------------------

                plateDetection.setSession(
                        session
                );


                plateDetectionEntities.add(
                        plateDetection
                );
            }


            // =====================================================
            // STEP 6
            // Connect detections to session
            // =====================================================

            session.setDetections(
                    plateDetectionEntities
            );


            // =====================================================
            // STEP 7
            // Save to database
            // =====================================================

            DetectionSessionEntity savedSession =
                    detectionSessionRepository.save(
                            session
                    );


            System.out.println(
                    "Detection session saved successfully."
            );

            System.out.println(
                    "Session ID: "
                            + savedSession.getId()
            );


            // =====================================================
            // STEP 8
            // Return session ID
            // =====================================================

            detectionResponse.setSessionId(
                    savedSession.getId()
            );


            return detectionResponse;


        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Error during number plate detection and database saving: "
                            + e.getMessage(),
                    e
            );
        }
    }
}