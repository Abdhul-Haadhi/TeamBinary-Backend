package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.VehicleSearchResponseDto;
import com.example.TeamBinary_Backend.Services.VehicleSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vehicle-search")
public class VehicleSearchController {

    private final VehicleSearchService vehicleSearchService;

    public VehicleSearchController(VehicleSearchService vehicleSearchService) {
        this.vehicleSearchService = vehicleSearchService;
    }

    @PostMapping("/search")
    public ResponseEntity<VehicleSearchResponseDto> searchVehicle(@RequestParam("file") MultipartFile file){
        VehicleSearchResponseDto response = vehicleSearchService.searchVehicle(file);

        return ResponseEntity.ok(response);
    }
}
