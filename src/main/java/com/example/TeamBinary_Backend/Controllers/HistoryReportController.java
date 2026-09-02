package com.example.TeamBinary_Backend.Controllers;


import com.example.TeamBinary_Backend.DTOs.HistoryReportDTO;
import com.example.TeamBinary_Backend.Services.HistoryReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports/history")
@CrossOrigin(origins = "http://localhost:5173")
public class HistoryReportController {

    private final HistoryReportService historyReportService;

    public HistoryReportController(
            HistoryReportService historyReportService
    ) {
        this.historyReportService = historyReportService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryReportDTO>> getAllHistoryReports() {

        List<HistoryReportDTO> reports =
                historyReportService.getAllHistoryReports();

        return ResponseEntity.ok(reports);
    }
}

