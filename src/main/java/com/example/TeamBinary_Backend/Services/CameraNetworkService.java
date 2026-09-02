package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CameraNetworkRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraNetworkResponseDTO;

import java.util.List;

public interface CameraNetworkService {
    CameraNetworkResponseDTO createNetwork(CameraNetworkRequestDTO requestDTO);
    List<CameraNetworkResponseDTO> getAllNetworks();
    CameraNetworkResponseDTO getNetworkById(Long networkId);
    CameraNetworkResponseDTO updateNetworkStatus(Long networkId, String newStatus);
    void deleteNetwork(Long networkId);
}
