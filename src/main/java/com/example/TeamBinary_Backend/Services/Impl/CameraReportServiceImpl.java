package com.example.TeamBinary_Backend.Services.Impl;


import com.example.TeamBinary_Backend.DTOs.CameraReportDTO;
import com.example.TeamBinary_Backend.Entities.CctvCameraEntity;
import com.example.TeamBinary_Backend.Repositories.CctvCameraRepository;
import com.example.TeamBinary_Backend.Services.CameraReportService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CameraReportServiceImpl implements CameraReportService {

    private final CctvCameraRepository cctvCameraRepository;

    public CameraReportServiceImpl(
            CctvCameraRepository cctvCameraRepository
    ) {
        this.cctvCameraRepository = cctvCameraRepository;
    }

    @Override
    public List<CameraReportDTO> getAllCameraReports() {

        List<CctvCameraEntity> cameras =
                cctvCameraRepository.findAll();

        return cameras.stream()
                .map(camera -> new CameraReportDTO(
                        camera.getId(),
                        camera.getCameraName(),
                        camera.getLocation(),
                        camera.getCameraType(),
                        camera.getStatus(),
                        camera.getDescription()
                ))
                .collect(Collectors.toList());
    }
}

