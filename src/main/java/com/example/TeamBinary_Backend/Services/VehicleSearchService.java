package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.VehicleSearchResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface VehicleSearchService {

    VehicleSearchResponseDto searchVehicle(MultipartFile file);
}
