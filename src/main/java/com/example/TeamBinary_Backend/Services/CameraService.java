package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CameraRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraResponseDTO;

import java.util.List;

public interface CameraService {
    List<CameraResponseDTO> getAllCameras();
    List<CameraResponseDTO> getCamerasByStatus(String status);
    List<CameraResponseDTO> getCamerasByNetwork(Long networkId);
    CameraResponseDTO getCameraById(Long cameraId);
    CameraResponseDTO  updateCameraStatus(Long cameraId, String newStatus);
    void deleteCamera(Long cameraId);
    CameraResponseDTO addCamera(CameraRequestDTO dto);
}
