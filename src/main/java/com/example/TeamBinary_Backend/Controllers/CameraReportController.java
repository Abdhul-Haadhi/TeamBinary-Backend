package com.example.TeamBinary_Backend.Controllers;


import com.example.TeamBinary_Backend.DTOs.CameraReportDTO;
import com.example.TeamBinary_Backend.Services.CameraReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports/cameras")
@CrossOrigin(origins = "http://localhost:5173")
public class CameraReportController {

    private final CameraReportService cameraReportService;

    public CameraReportController(
            CameraReportService cameraReportService
    ) {
        this.cameraReportService = cameraReportService;
    }

    @GetMapping
    public ResponseEntity<List<CameraReportDTO>> getAllCameraReports() {

        List<CameraReportDTO> reports =
                cameraReportService.getAllCameraReports();

        return ResponseEntity.ok(reports);
    }
}

