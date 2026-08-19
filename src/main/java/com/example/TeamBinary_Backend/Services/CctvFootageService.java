package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CctvFootageDto;
import com.example.TeamBinary_Backend.DTOs.CctvVideoProcessingResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CctvFootageService {

    CctvFootageDto uploadFootage(Long cameraId, MultipartFile file);

    List<CctvFootageDto> getFootageByCamera(Long cameraId);

    void deleteFootage(Long footageId);

    CctvVideoProcessingResponseDto processFootage(Long footageId);
}
