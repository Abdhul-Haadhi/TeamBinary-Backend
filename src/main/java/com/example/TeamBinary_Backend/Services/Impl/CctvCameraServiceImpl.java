package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CctvCameraDto;
import com.example.TeamBinary_Backend.Entities.CctvCameraEntity;
import com.example.TeamBinary_Backend.Repositories.CctvCameraRepository;
import com.example.TeamBinary_Backend.Services.CctvCameraService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CctvCameraServiceImpl implements CctvCameraService {

    private final CctvCameraRepository cameraRepository;

    public CctvCameraServiceImpl(CctvCameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    @Override
    public CctvCameraDto createCamera(CctvCameraDto dto) {

        CctvCameraEntity camera = new CctvCameraEntity();

        camera.setCameraName(dto.getCameraName());
        camera.setLocation(dto.getLocation());
        camera.setCameraType(dto.getCameraType());
        camera.setDescription(dto.getDescription());
        camera.setCameraUrl(dto.getCameraUrl());
        camera.setStatus("OFFLINE");

        CctvCameraEntity saved = cameraRepository.save(camera);

        return convertToDto(saved);
    }


    @Override
    public List<CctvCameraDto> getAllCameras() {

        List<CctvCameraEntity> cameras = cameraRepository.findAll();

        List<CctvCameraDto> result = new ArrayList<>();


        for (CctvCameraEntity camera : cameras) {

            result.add(convertToDto(camera));
        }

        return result;
    }


    @Override
    public CctvCameraDto getCameraById(Long id) {

        CctvCameraEntity camera = cameraRepository.findById(id).orElseThrow(() -> new RuntimeException("Camera not found: " + id));

        return convertToDto(camera);
    }


    @Override
    public void deleteCamera(Long id) {

        if (!cameraRepository.existsById(id)) {

            throw new RuntimeException("Camera not found: " + id);
        }

        cameraRepository.deleteById(id);
    }


    private CctvCameraDto convertToDto(CctvCameraEntity camera) {

        CctvCameraDto dto = new CctvCameraDto();

        dto.setId(camera.getId());
        dto.setCameraName(camera.getCameraName());
        dto.setLocation(camera.getLocation());
        dto.setCameraType(camera.getCameraType());
        dto.setDescription(camera.getDescription());
        dto.setCameraUrl(camera.getCameraUrl());
        dto.setStatus(camera.getStatus());

        return dto;
    }
}
