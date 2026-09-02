package com.example.TeamBinary_Backend.DTOs;

public class CameraResponseDTO {
    private Long   cameraId;
    private String ipAddress;
    private String location;
    private String status;
    private Long   networkId;
    private String networkName;

    // ── Constructors ──────────────────────────────────────────
    public CameraResponseDTO() {}

    public CameraResponseDTO(Long cameraId, String ipAddress, String location,
                             String status, Long networkId, String networkName) {
        this.cameraId    = cameraId;
        this.ipAddress   = ipAddress;
        this.location    = location;
        this.status      = status;
        this.networkId   = networkId;
        this.networkName = networkName;
    }

    // ── Getters ───────────────────────────────────────────────
    public Long   getCameraId()    { return cameraId;    }
    public String getIpAddress()   { return ipAddress;   }
    public String getLocation()    { return location;    }
    public String getStatus()      { return status;      }
    public Long   getNetworkId()   { return networkId;   }
    public String getNetworkName() { return networkName; }

    // ── Setters ───────────────────────────────────────────────
    public void setCameraId(Long cameraId)       { this.cameraId    = cameraId;    }
    public void setIpAddress(String ipAddress)   { this.ipAddress   = ipAddress;   }
    public void setLocation(String location)     { this.location    = location;    }
    public void setStatus(String status)         { this.status      = status;      }
    public void setNetworkId(Long networkId)     { this.networkId   = networkId;   }
    public void setNetworkName(String name)      { this.networkName = name;        }

}
