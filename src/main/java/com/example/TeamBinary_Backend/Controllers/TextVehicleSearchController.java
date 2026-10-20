package com.example.TeamBinary_Backend.Controllers;


import com.example.TeamBinary_Backend.DTOs.TextVehicleSearchRequestDto;
import com.example.TeamBinary_Backend.Services.TextVehicleSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class TextVehicleSearchController {

    private final TextVehicleSearchService textVehicleSearchService;

    public TextVehicleSearchController(TextVehicleSearchService textVehicleSearchService) {
        this.textVehicleSearchService = textVehicleSearchService;
    }

    @PostMapping("/text-search")
    public ResponseEntity<Map<String, Object>> searchByDescription(
            @RequestBody TextVehicleSearchRequestDto request
    ) {
        // Validate the description.
        if (request == null
                || !StringUtils.hasText(request.getDescription())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a vehicle description."
            );
        }

        String description = request.getDescription().trim();

        if (description.length() > 500) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The description cannot exceed 500 characters."
            );
        }

        // Send the description to the Python AI service.
        Map<String, Object> result =
                textVehicleSearchService.searchVehiclesByDescription(
                        description
                );

        return ResponseEntity.ok(result);
    }
}
