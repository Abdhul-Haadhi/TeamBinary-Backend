package com.example.TeamBinary_Backend.DTOs;

import com.example.TeamBinary_Backend.Enum.NetworkStatus;

public class CameraNetworkResponseDTO {
    private Long          networkId;
    private String        networkName;
    private NetworkStatus status;
    private int           totalCameras;
    private int           activeCameras;

    public CameraNetworkResponseDTO() {
    }

    public CameraNetworkResponseDTO(Long networkId, String networkName, NetworkStatus status, int totalCameras, int activeCameras) {
        this.networkId = networkId;
        this.networkName = networkName;
        this.status = status;
        this.totalCameras = totalCameras;
        this.activeCameras = activeCameras;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public NetworkStatus getStatus() {
        return status;
    }

    public void setStatus(NetworkStatus status) {
        this.status = status;
    }

    public int getTotalCameras() {
        return totalCameras;
    }

    public void setTotalCameras(int totalCameras) {
        this.totalCameras = totalCameras;
    }

    public int getActiveCameras() {
        return activeCameras;
    }

    public void setActiveCameras(int activeCameras) {
        this.activeCameras = activeCameras;
    }
}
