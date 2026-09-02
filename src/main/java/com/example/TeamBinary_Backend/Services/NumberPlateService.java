package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.DetectionResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface NumberPlateService {

    DetectionResponseDto detectNumberPlate(MultipartFile file);
}
