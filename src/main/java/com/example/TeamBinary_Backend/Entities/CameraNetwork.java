package com.example.TeamBinary_Backend.Entities;

import com.example.TeamBinary_Backend.Enum.NetworkStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "camera_network")
public class CameraNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long networkId;

    @Column( nullable = false, length = 100)
    private String networkName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NetworkStatus status;

    // ── Constructors ──────────────────────────────────────────
    public CameraNetwork() {
        this.status = NetworkStatus.ACTIVE;
    }

    public CameraNetwork(String networkName, NetworkStatus status) {
        this.networkName = networkName;
        this.status      = status;
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
}
