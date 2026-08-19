package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CctvCameraDto;

import java.util.List;

public interface CctvCameraService {

    CctvCameraDto createCamera(CctvCameraDto cameraDto);

    List<CctvCameraDto> getAllCameras();

    CctvCameraDto getCameraById(Long id);

    void deleteCamera(Long id);
}
