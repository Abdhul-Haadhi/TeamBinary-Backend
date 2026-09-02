package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.VehicleClassificationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehicleClassificationService {
    VehicleClassificationDTO classifyVehicle(MultipartFile image) throws IOException;

    List<VehicleClassificationDTO> getAll();
}
