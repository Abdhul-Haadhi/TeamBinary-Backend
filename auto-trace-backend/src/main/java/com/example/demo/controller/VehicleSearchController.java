package com.example.demo.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleSearchController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/search")
    public ResponseEntity<?> searchVehicle(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            // =====================================================
            // VALIDATE FILE
            // =====================================================

            if (file == null || file.isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                java.util.Map.of(
                                        "success", false,
                                        "message",
                                        "Please upload an image"
                                )
                        );
            }

            // =====================================================
            // CREATE MULTIPART BODY
            // =====================================================

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            ByteArrayResource resource =
                    new ByteArrayResource(file.getBytes()) {

                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    };

            body.add("file", resource);

            // =====================================================
            // HEADERS
            // =====================================================

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(
                    MediaType.MULTIPART_FORM_DATA
            );

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            // =====================================================
            // FASTAPI AI SERVICE
            // =====================================================

            String aiUrl =
                    "http://127.0.0.1:8000/search";

            System.out.println(
                    "Sending vehicle image to AI service..."
            );

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            aiUrl,
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            // =====================================================
            // PRINT AI RESPONSE
            // =====================================================

            System.out.println(
                    "AI SERVICE RESPONSE:"
            );

            System.out.println(
                    response.getBody()
            );

            // =====================================================
            // RETURN AI RESPONSE TO REACT
            // =====================================================

            return ResponseEntity
                    .status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(
                            org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
                    )
                    .body(
                            java.util.Map.of(
                                    "success", false,
                                    "message",
                                    "AI vehicle search failed",
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }
}