package com.example.TeamBinary_Backend.DTOs;



public class CameraRequestDTO {
    private String ipAddress;
    private String location;
    private Long   networkId;
    private String status;   // "ACTIVE" | "INACTIVE" | "OFFLINE"

    // ── Constructors ──────────────────────────────────────────
    public CameraRequestDTO() {}

    public CameraRequestDTO(String ipAddress, String location, Long networkId, String status) {
        this.ipAddress = ipAddress;
        this.location  = location;
        this.networkId = networkId;
        this.status    = status;
    }

    // ── Getters ───────────────────────────────────────────────
    public String getIpAddress() { return ipAddress; }
    public String getLocation()  { return location;  }
    public Long   getNetworkId() { return networkId; }
    public String getStatus()    { return status;    }

    // ── Setters ───────────────────────────────────────────────
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public void setLocation(String location)   { this.location  = location;  }
    public void setNetworkId(Long networkId)   { this.networkId = networkId; }
    public void setStatus(String status)       { this.status    = status;    }
}








