package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.VehicleSearchResponseDto;
import com.example.TeamBinary_Backend.DTOs.VehicleTextSearchRequestDto;
import com.example.TeamBinary_Backend.Services.VehicleSearchService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleSearchController {

    private final VehicleSearchService vehicleSearchService;

    public VehicleSearchController(
            VehicleSearchService vehicleSearchService
    ) {
        this.vehicleSearchService = vehicleSearchService;
    }

    // ========================================================
    // IMAGE-BASED VEHICLE SEARCH
    // POST /api/vehicles/search
    // Multipart form field: file
    // ========================================================

    @PostMapping(
            value = "/search",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<VehicleSearchResponseDto> searchByImage(
            @RequestParam("file") MultipartFile file
    ) {
        VehicleSearchResponseDto response =
                vehicleSearchService.searchByImage(file);

        return ResponseEntity.ok(response);
    }

    // ========================================================
    // TEXT-BASED VEHICLE SEARCH
    // POST /api/vehicles/text-search
    // JSON: {"description":"white SUV"}
    // ========================================================

    @PostMapping(
            value = "/text-search",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<VehicleSearchResponseDto> searchByDescription(
            @RequestBody VehicleTextSearchRequestDto request
    ) {
        if (request == null
                || !StringUtils.hasText(request.getDescription())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a vehicle description."
            );
        }

        VehicleSearchResponseDto response =
                vehicleSearchService.searchByDescription(
                        request.getDescription()
                );

        return ResponseEntity.ok(response);
    }
}