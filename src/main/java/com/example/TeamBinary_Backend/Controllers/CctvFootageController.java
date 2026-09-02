package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CctvFootageDto;
import com.example.TeamBinary_Backend.DTOs.CctvVideoProcessingResponseDto;
import com.example.TeamBinary_Backend.Services.CctvFootageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cameras")
public class CctvFootageController {

    private final CctvFootageService footageService;

    public CctvFootageController(CctvFootageService footageService) {
        this.footageService = footageService;
    }

    @PostMapping("/{cameraId}/footage")
    public ResponseEntity<CctvFootageDto> uploadFootage(@PathVariable Long cameraId, @RequestParam("file") MultipartFile file) {

        CctvFootageDto footage = footageService.uploadFootage(cameraId, file);

        return ResponseEntity.ok(footage);
    }

    @PostMapping("/footage/{footageId}/process")
    public ResponseEntity<CctvVideoProcessingResponseDto> processFootage(@PathVariable Long footageId) {

        CctvVideoProcessingResponseDto response = footageService.processFootage(footageId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{cameraId}/footage")
    public ResponseEntity<List<CctvFootageDto>> getFootage(@PathVariable Long cameraId) {
        return ResponseEntity.ok(footageService.getFootageByCamera(cameraId));
    }


    @DeleteMapping("/footage/{footageId}")
    public ResponseEntity<Void> deleteFootage(@PathVariable Long footageId) {

        footageService.deleteFootage(footageId);

        return ResponseEntity.noContent().build();
    }
}
