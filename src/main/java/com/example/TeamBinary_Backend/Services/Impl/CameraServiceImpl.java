package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CameraRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraResponseDTO;
import com.example.TeamBinary_Backend.Entities.Camera;
import com.example.TeamBinary_Backend.Entities.CameraNetwork;
import com.example.TeamBinary_Backend.Entities.CameraStatus;
import com.example.TeamBinary_Backend.Repositories.CameraNetworkRepository;
import com.example.TeamBinary_Backend.Repositories.CameraRepository;
import com.example.TeamBinary_Backend.Services.CameraService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CameraServiceImpl implements CameraService {

    private final CameraRepository cameraRepository;
    private final CameraNetworkRepository networkRepository;

    // Constructor injection — no Lombok needed
    public CameraServiceImpl(CameraRepository cameraRepository,
                             CameraNetworkRepository networkRepository) {
        this.cameraRepository  = cameraRepository;
        this.networkRepository = networkRepository;
    }

    // ── Add Camera ────────────────────────────────────────────
    @Override
    public CameraResponseDTO addCamera(CameraRequestDTO dto) {

        // Manual validation
        if (dto.getIpAddress() == null || dto.getIpAddress().trim().isEmpty()) {
            throw new RuntimeException("IP address is required");
        }
        if (dto.getLocation() == null || dto.getLocation().trim().isEmpty()) {
            throw new RuntimeException("Location is required");
        }
        if (dto.getNetworkId() == null) {
            throw new RuntimeException("Network ID is required");
        }
        if (cameraRepository.existsByIpAddress(dto.getIpAddress())) {
            throw new RuntimeException("Camera with this IP already exists: " + dto.getIpAddress());
        }

        // Find network
        Optional<CameraNetwork> networkOpt = networkRepository.findById(dto.getNetworkId());
        if (!networkOpt.isPresent()) {
            throw new RuntimeException("Network not found with ID: " + dto.getNetworkId());
        }
        CameraNetwork network = networkOpt.get();

        // Parse status — default to ACTIVE if null or invalid
        CameraStatus status = CameraStatus.ACTIVE;
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                status = CameraStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status value: " + dto.getStatus());
            }
        }

        // Build and save
        Camera camera = new Camera(
                dto.getIpAddress().trim(),
                dto.getLocation().trim(),
                status,
                network
        );

        Camera saved = cameraRepository.save(camera);
        return convertToResponse(saved);
    }

    // ── Get All ───────────────────────────────────────────────
    @Override
    public List<CameraResponseDTO> getAllCameras() {
        List<Camera> cameras = cameraRepository.findAll();
        List<CameraResponseDTO> result = new ArrayList<>();
        for (Camera c : cameras) {
            result.add(convertToResponse(c));
        }
        return result;
    }

    // ── Get By Status ─────────────────────────────────────────
    @Override
    public List<CameraResponseDTO> getCamerasByStatus(String status) {
        CameraStatus cameraStatus;
        try {
            cameraStatus = CameraStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
        List<Camera> cameras = cameraRepository.findByStatus(cameraStatus);
        List<CameraResponseDTO> result = new ArrayList<>();
        for (Camera c : cameras) {
            result.add(convertToResponse(c));
        }
        return result;
    }

    // ── Get By Network ────────────────────────────────────────
    @Override
    public List<CameraResponseDTO> getCamerasByNetwork(Long networkId) {
        List<Camera> cameras = cameraRepository.findByNetwork_NetworkId(networkId);
        List<CameraResponseDTO> result = new ArrayList<>();
        for (Camera c : cameras) {
            result.add(convertToResponse(c));
        }
        return result;
    }

    // ── Get By ID ─────────────────────────────────────────────
    @Override
    public CameraResponseDTO getCameraById(Long cameraId) {
        Optional<Camera> cameraOpt = cameraRepository.findById(cameraId);
        if (!cameraOpt.isPresent()) {
            throw new RuntimeException("Camera not found with ID: " + cameraId);
        }
        return convertToResponse(cameraOpt.get());
    }

    // ── Update Status ─────────────────────────────────────────
    @Override
    public CameraResponseDTO updateCameraStatus(Long cameraId, String newStatus) {
        Optional<Camera> cameraOpt = cameraRepository.findById(cameraId);
        if (!cameraOpt.isPresent()) {
            throw new RuntimeException("Camera not found with ID: " + cameraId);
        }

        CameraStatus status;
        try {
            status = CameraStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus);
        }

        Camera camera = cameraOpt.get();
        camera.setStatus(status);
        Camera updated = cameraRepository.save(camera);
        return convertToResponse(updated);
    }

    // ── Delete ────────────────────────────────────────────────
    @Override
    public void deleteCamera(Long cameraId) {
        Optional<Camera> cameraOpt = cameraRepository.findById(cameraId);
        if (!cameraOpt.isPresent()) {
            throw new RuntimeException("Camera not found with ID: " + cameraId);
        }
        cameraRepository.delete(cameraOpt.get());
    }

    // ── Convert entity → DTO ──────────────────────────────────
    private CameraResponseDTO convertToResponse(Camera camera) {
        CameraResponseDTO dto = new CameraResponseDTO();
        dto.setCameraId(camera.getCameraId());
        dto.setIpAddress(camera.getIpAddress());
        dto.setLocation(camera.getLocation());
        dto.setStatus(camera.getStatus().name());

        if (camera.getNetwork() != null) {
            dto.setNetworkId(camera.getNetwork().getNetworkId());
            dto.setNetworkName(camera.getNetwork().getNetworkName());
        } else {
            dto.setNetworkId(null);
            dto.setNetworkName("Unassigned");
        }
        return dto;
    }
}
