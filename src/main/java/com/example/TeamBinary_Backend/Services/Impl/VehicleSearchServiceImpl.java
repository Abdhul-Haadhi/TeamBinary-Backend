
package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.VehicleSearchResponseDto;
import com.example.TeamBinary_Backend.Entities.VehicleSearchMatchEntity;
import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchSessionRepository;
import com.example.TeamBinary_Backend.Services.VehicleSearchService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleSearchServiceImpl
        implements VehicleSearchService {

    private final RestTemplate restTemplate;

    private final VehicleSearchSessionRepository sessionRepository;

    @Value("${autotrace.ai.image-search-url:http://127.0.0.1:8000/vehicles/search}")
    private String imageSearchUrl;

    @Value("${autotrace.ai.text-search-url:http://127.0.0.1:8000/text-search}")
    private String textSearchUrl;

    public VehicleSearchServiceImpl(
            RestTemplate restTemplate,
            VehicleSearchSessionRepository sessionRepository
    ) {
        this.restTemplate = restTemplate;
        this.sessionRepository = sessionRepository;
    }

    // ========================================================
    // IMAGE SEARCH
    // Spring Boot receives an image and forwards it to FastAPI.
    // ========================================================

    @Override
    public VehicleSearchResponseDto searchByImage(
            MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please upload a vehicle image."
            );
        }

        try {
            String fileName = file.getOriginalFilename();

            if (fileName == null || fileName.isBlank()) {
                fileName = "vehicle-image.jpg";
            }

            // Preserve the filename in the multipart request.
            final String uploadedFileName = fileName;

            ByteArrayResource resource =
                    new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return uploadedFileName;
                        }
                    };

            HttpHeaders partHeaders = new HttpHeaders();

            String contentType = file.getContentType();

            try {
                partHeaders.setContentType(
                        contentType == null
                                ? MediaType.APPLICATION_OCTET_STREAM
                                : MediaType.parseMediaType(contentType)
                );
            } catch (IllegalArgumentException exception) {
                partHeaders.setContentType(
                        MediaType.APPLICATION_OCTET_STREAM
                );
            }

            HttpEntity<ByteArrayResource> filePart =
                    new HttpEntity<>(resource, partHeaders);

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            // FastAPI expects the multipart field named "file".
            body.add("file", filePart);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            Map<String, Object> aiResponse =
                    callAiService(imageSearchUrl, request);

            VehicleSearchResponseDto response =
                    convertResponse(aiResponse);

            if (response.getFilename() == null) {
                response.setFilename(uploadedFileName);
            }

            // Store the search and its CCTV match metadata.
            saveSearch(
                    "IMAGE",
                    uploadedFileName,
                    null,
                    response
            );

            return response;

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to read the uploaded image.",
                    exception
            );
        }
    }

    // ========================================================
    // TEXT SEARCH
    // FastAPI expects JSON: {"description": "..."}.
    // ========================================================

    @Override
    public VehicleSearchResponseDto searchByDescription(
            String description
    ) {
        if (description == null || description.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a vehicle description."
            );
        }

        String query = description.trim();

        if (query.length() > 500) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The description cannot exceed 500 characters."
            );
        }

        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("description", query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(requestBody, headers);

        Map<String, Object> aiResponse =
                callAiService(textSearchUrl, request);

        VehicleSearchResponseDto response =
                convertResponse(aiResponse);

        if (response.getQuery() == null) {
            response.setQuery(query);
        }

        saveSearch(
                "TEXT",
                null,
                query,
                response
        );

        return response;
    }

    // ========================================================
    // CALL THE FASTAPI SERVICE
    // ========================================================

    private Map<String, Object> callAiService(
            String url,
            HttpEntity<?> request
    ) {
        try {
            ResponseEntity<Map<String, Object>> aiResponse =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            new ParameterizedTypeReference<
                                    Map<String, Object>
                                    >() {
                            }
                    );

            Map<String, Object> body = aiResponse.getBody();

            if (body == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "The AI service returned an empty response."
                );
            }

            return body;

        } catch (HttpStatusCodeException exception) {
            // FastAPI returned a non-2xx response.
            String detail = exception.getResponseBodyAsString();

            System.err.println(
                    "FastAPI HTTP error: "
                            + exception.getStatusCode()
                            + ", response: "
                            + detail
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "The AI service returned an error: "
                            + detail,
                    exception
            );

        } catch (ResourceAccessException exception) {
            // FastAPI is stopped or unreachable.
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Unable to connect to FastAPI at " + url,
                    exception
            );
        }
    }

    // ========================================================
    // CONVERT FASTAPI JSON INTO THE RESPONSE DTO
    // ========================================================

    private VehicleSearchResponseDto convertResponse(
            Map<String, Object> payload
    ) {
        VehicleSearchResponseDto response =
                new VehicleSearchResponseDto();

        response.setSuccess(
                !Boolean.FALSE.equals(payload.get("success"))
        );

        response.setFilename(
                asString(payload.get("filename"))
        );

        response.setQuery(
                asString(payload.get("query"))
        );

        response.setVehicleType(
                asString(payload.get("vehicleType"))
        );

        response.setColor(
                asString(payload.get("color"))
        );

        response.setDetectionConfidence(
                asDouble(payload.get("detectionConfidence"))
        );

        response.setVideosScanned(
                asInteger(payload.get("videosScanned"))
        );

        response.setMessage(
                asString(payload.get("message"))
        );

        // Preserve the structure expected by the React components.
        response.setMatches(
                toMapList(payload.get("matches"))
        );

        response.setResults(
                toMapList(payload.get("results"))
        );

        return response;
    }

    private List<Map<String, Object>> toMapList(Object value) {
        List<Map<String, Object>> output = new ArrayList<>();

        if (!(value instanceof List<?> list)) {
            return output;
        }

        for (Object item : list) {
            if (item instanceof Map<?, ?> map) {
                Map<String, Object> converted =
                        new LinkedHashMap<>();

                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    converted.put(
                            String.valueOf(entry.getKey()),
                            entry.getValue()
                    );
                }

                output.add(converted);
            }
        }

        return output;
    }

    // ========================================================
    // SAVE SEARCH SESSION AND MATCH METADATA
    // Base64 images are returned to React, not stored in MySQL.
    // ========================================================

    private void saveSearch(
            String searchType,
            String fileName,
            String description,
            VehicleSearchResponseDto response
    ) {
        VehicleSearchSessionEntity session =
                new VehicleSearchSessionEntity();

        session.setSearchType(searchType);
        session.setOriginalFileName(fileName);
        session.setQueryDescription(description);
        session.setVehicleType(response.getVehicleType());
        session.setColor(response.getColor());
        session.setCreatedAt(LocalDateTime.now());
        session.setVideosScanned(response.getVideosScanned());

        // Image search uses "matches"; text search uses "results".
        List<Map<String, Object>> source =
                !response.getMatches().isEmpty()
                        ? response.getMatches()
                        : response.getResults();

        session.setTotalMatches(source.size());

        List<VehicleSearchMatchEntity> entities =
                new ArrayList<>();

        for (Map<String, Object> matchData : source) {
            VehicleSearchMatchEntity match =
                    new VehicleSearchMatchEntity();

            match.setSearchSession(session);

            match.setCameraName(
                    asString(matchData.get("cameraName"))
            );

            match.setCameraLocation(
                    asString(matchData.get("cameraLocation"))
            );

            match.setVideoFileName(
                    asString(matchData.get("videoFileName"))
            );

            match.setFrameNumber(
                    asInteger(matchData.get("frameNumber"))
            );

            match.setTimestamp(
                    asString(matchData.get("timestamp"))
            );

            match.setTimestampSeconds(
                    asDouble(matchData.get("timestampSeconds"))
            );

            match.setVehicleType(
                    asString(matchData.get("vehicleType"))
            );

            match.setColor(
                    asString(matchData.get("color"))
            );

            match.setClassName(
                    asString(matchData.get("className"))
            );

            match.setDetectedPlate(
                    asString(matchData.get("detectedPlate"))
            );

            match.setPlateConfidence(
                    asDouble(matchData.get("plateConfidence"))
            );

            match.setOcrConfidence(
                    asDouble(matchData.get("ocrConfidence"))
            );

            match.setConfidence(
                    asDouble(matchData.get("confidence"))
            );

            match.setMatchConfidence(
                    asDouble(matchData.get("matchConfidence"))
            );

            match.setVisualSimilarity(
                    asDouble(matchData.get("visualSimilarity"))
            );

            match.setX1(asDouble(matchData.get("x1")));
            match.setY1(asDouble(matchData.get("y1")));
            match.setX2(asDouble(matchData.get("x2")));
            match.setY2(asDouble(matchData.get("y2")));

            entities.add(match);
        }

        session.setMatches(entities);

        VehicleSearchSessionEntity saved =
                sessionRepository.save(session);

        response.setSearchSessionId(saved.getId());
    }

    // ========================================================
    // SAFE VALUE CONVERSION HELPERS
    // ========================================================

    private String asString(Object value) {
        if (value == null) {
            return null;
        }

        return String.valueOf(value);
    }

    private Double asDouble(Object value) {
        if (value instanceof Number number) {
            return number.doubleValue();
        }

        if (value != null) {
            try {
                return Double.parseDouble(
                        String.valueOf(value)
                );
            } catch (NumberFormatException ignored) {
                // Missing or invalid numeric field.
            }
        }

        return null;
    }

    private Integer asInteger(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }

        if (value != null) {
            try {
                return Integer.parseInt(
                        String.valueOf(value)
                );
            } catch (NumberFormatException ignored) {
                // Missing or invalid integer field.
            }
        }

        return null;
    }
}
