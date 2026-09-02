package com.example.TeamBinary_Backend.Controllers;


import com.example.TeamBinary_Backend.DTOs.VehicleSearchReportDTO;
import com.example.TeamBinary_Backend.DTOs.VehicleSearchReportDTO;
import com.example.TeamBinary_Backend.Services.VehicleSearchReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports/vehicle-search")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleSearchReportController {

    private final VehicleSearchReportService vehicleSearchReportService;

    public VehicleSearchReportController(
            VehicleSearchReportService vehicleSearchReportService
    ) {
        this.vehicleSearchReportService =
                vehicleSearchReportService;
    }

    /*
     * Get all vehicle search results
     */
    @GetMapping
    public ResponseEntity<List<VehicleSearchReportDTO>>
    getAllVehicleSearchReports() {

        return ResponseEntity.ok(
                vehicleSearchReportService
                        .getAllVehicleSearchReports()
        );
    }

    /*
     * Get results for one search session
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<VehicleSearchReportDTO>>
    getVehicleSearchReportBySession(
            @PathVariable Long sessionId
    ) {

        return ResponseEntity.ok(
                vehicleSearchReportService
                        .getVehicleSearchReportBySession(sessionId)
        );
    }
}
