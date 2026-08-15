package com.example.TeamBinary_Backend.DTOs;

public class CameraNetworkRequestDTO {

    private String networkName;
    private String status;

    public CameraNetworkRequestDTO() {
    }

    public CameraNetworkRequestDTO(String networkName, String status) {
        this.networkName = networkName;
        this.status = status;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}