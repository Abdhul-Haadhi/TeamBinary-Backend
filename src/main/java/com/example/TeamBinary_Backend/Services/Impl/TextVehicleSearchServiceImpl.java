package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.Services.TextVehicleSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class TextVehicleSearchServiceImpl implements TextVehicleSearchService {

    private final RestTemplate restTemplate;

    @Value("${autotrace.ai.text-search-url:http://127.0.0.1:8000/text-search}")

    private String aiServiceUrl;

    public TextVehicleSearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> searchVehiclesByDescription(
            String description
    ) {
        try {
            // Build the JSON request for FastAPI.
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("description", description);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(requestBody, headers);

            // Call the Python text-search endpoint.
            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            aiServiceUrl,
                            HttpMethod.POST,
                            request,
                            new ParameterizedTypeReference<
                                                                Map<String, Object>
                                                                >() {
                            }
                    );

            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "The AI service returned an empty response."
                );
            }

            return responseBody;

        } catch (HttpStatusCodeException exception) {
            // The Python service returned an HTTP error.
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Text-search AI service returned an error: "
                            + exception.getResponseBodyAsString(),
                    exception
            );

        } catch (ResourceAccessException exception) {
            // FastAPI is not running or cannot be reached.
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Unable to connect to the Python AI service. "
                            + "Please check that FastAPI is running.",
                    exception
            );
        }
    }
}
