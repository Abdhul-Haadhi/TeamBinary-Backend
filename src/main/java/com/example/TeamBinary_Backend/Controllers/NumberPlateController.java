package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.DetectionResponseDto;
import com.example.TeamBinary_Backend.Services.NumberPlateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/number-plate")
public class NumberPlateController {

    private final NumberPlateService numberPlateService;

    public NumberPlateController(NumberPlateService numberPlateService) {
        this.numberPlateService = numberPlateService;
    }

    @PostMapping("/detect")
    public ResponseEntity<DetectionResponseDto> detect(
            @RequestParam("file") MultipartFile file) {

        DetectionResponseDto response = numberPlateService.detectNumberPlate(file);

        return ResponseEntity.ok(response);
    }
}
