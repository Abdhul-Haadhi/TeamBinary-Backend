package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CameraNetworkRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraNetworkResponseDTO;
import com.example.TeamBinary_Backend.Entities.Camera;
import com.example.TeamBinary_Backend.Entities.CameraNetwork;
import com.example.TeamBinary_Backend.Entities.CameraStatus;
import com.example.TeamBinary_Backend.Entities.NetworkStatus;
import com.example.TeamBinary_Backend.Repositories.CameraNetworkRepository;
import com.example.TeamBinary_Backend.Repositories.CameraRepository;
import com.example.TeamBinary_Backend.Services.CameraNetworkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CameraNetworkServiceImpl implements CameraNetworkService {

    private final CameraNetworkRepository networkRepository;
    private final CameraRepository cameraRepository;

    public CameraNetworkServiceImpl(CameraNetworkRepository networkRepository,
                                    CameraRepository cameraRepository) {
        this.networkRepository = networkRepository;
        this.cameraRepository  = cameraRepository;
    }

    // ── Create Network ────────────────────────────────────────
    @Override
    public CameraNetworkResponseDTO createNetwork(CameraNetworkRequestDTO dto) {

        // Manual validation
        if (dto.getNetworkName() == null || dto.getNetworkName().trim().isEmpty()) {
            throw new RuntimeException("Network name is required");
        }
        if (networkRepository.existsByNetworkName(dto.getNetworkName().trim())) {
            throw new RuntimeException("Network name already exists: " + dto.getNetworkName());
        }

        // Parse status — default ACTIVE
        NetworkStatus status = NetworkStatus.ACTIVE;
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                status = NetworkStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status: " + dto.getStatus());
            }
        }

        CameraNetwork network = new CameraNetwork(dto.getNetworkName().trim(), status);
        CameraNetwork saved   = networkRepository.save(network);
        return convertToResponse(saved);
    }

    // ── Get All Networks ──────────────────────────────────────
    @Override
    public List<CameraNetworkResponseDTO> getAllNetworks() {
        List<CameraNetwork> networks = networkRepository.findAll();
        List<CameraNetworkResponseDTO> result = new ArrayList<>();
        for (CameraNetwork n : networks) {
            result.add(convertToResponse(n));
        }
        return result;
    }

    // ── Get One Network ───────────────────────────────────────
    @Override
    public CameraNetworkResponseDTO getNetworkById(Long networkId) {
        Optional<CameraNetwork> networkOpt = networkRepository.findById(networkId);
        if (!networkOpt.isPresent()) {
            throw new RuntimeException("Network not found with ID: " + networkId);
        }
        return convertToResponse(networkOpt.get());
    }

    // ── Update Network Status ─────────────────────────────────
    @Override
    public CameraNetworkResponseDTO updateNetworkStatus(Long networkId, String newStatus) {
        Optional<CameraNetwork> networkOpt = networkRepository.findById(networkId);
        if (!networkOpt.isPresent()) {
            throw new RuntimeException("Network not found with ID: " + networkId);
        }

        NetworkStatus status;
        try {
            status = NetworkStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus);
        }

        CameraNetwork network = networkOpt.get();
        network.setStatus(status);
        CameraNetwork updated = networkRepository.save(network);
        return convertToResponse(updated);
    }

    // ── Delete Network ────────────────────────────────────────
    @Override
    public void deleteNetwork(Long networkId) {
        Optional<CameraNetwork> networkOpt = networkRepository.findById(networkId);
        if (!networkOpt.isPresent()) {
            throw new RuntimeException("Network not found with ID: " + networkId);
        }

        // Unassign all cameras in this network before deleting
        List<Camera> cameras = cameraRepository.findByNetwork_NetworkId(networkId);
        for (Camera camera : cameras) {
            camera.setNetwork(null);
            cameraRepository.save(camera);
        }

        networkRepository.delete(networkOpt.get());
    }

    // ── Convert entity → DTO ──────────────────────────────────
    private CameraNetworkResponseDTO convertToResponse(CameraNetwork network) {
        int total  = cameraRepository.countByNetwork_NetworkId(network.getNetworkId());
        int active = cameraRepository.countByNetwork_NetworkIdAndStatus(
                network.getNetworkId(), CameraStatus.ACTIVE
        );

        CameraNetworkResponseDTO dto = new CameraNetworkResponseDTO();
        dto.setNetworkId(network.getNetworkId());
        dto.setNetworkName(network.getNetworkName());
        dto.setStatus(NetworkStatus.valueOf(network.getStatus().name()));
        dto.setTotalCameras(total);
        dto.setActiveCameras(active);
        return dto;
    }
}
