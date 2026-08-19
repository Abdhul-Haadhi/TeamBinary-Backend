package com.example.TeamBinary_Backend.Entities;


import com.example.TeamBinary_Backend.Enum.CameraStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "camera")

public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long cameraId;

    @Column( nullable = false, length = 50)
    private String ipAddress;

    @Column( length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private CameraStatus status;

    @ManyToOne(fetch = FetchType.EAGER)

    private CameraNetwork network;

    // ── Constructors ──────────────────────────────────────────
    public Camera() {
        this.status = CameraStatus.ACTIVE;
    }

    public Camera(String ipAddress, String location, CameraStatus status, CameraNetwork network) {
        this.ipAddress = ipAddress;
        this.location  = location;
        this.status    = status;
        this.network   = network;
    }

    // ── Getters ───────────────────────────────────────────────
    public Long getCameraId()         { return cameraId;  }
    public String getIpAddress()      { return ipAddress; }
    public String getLocation()       { return location;  }
    public CameraStatus getStatus()   { return status;    }
    public CameraNetwork getNetwork() { return network;   }

    // ── Setters ───────────────────────────────────────────────
    public void setCameraId(Long cameraId)          { this.cameraId  = cameraId;  }
    public void setIpAddress(String ipAddress)      { this.ipAddress = ipAddress; }
    public void setLocation(String location)        { this.location  = location;  }
    public void setStatus(CameraStatus status)      { this.status    = status;    }
    public void setNetwork(CameraNetwork network)   { this.network   = network;   }

}
