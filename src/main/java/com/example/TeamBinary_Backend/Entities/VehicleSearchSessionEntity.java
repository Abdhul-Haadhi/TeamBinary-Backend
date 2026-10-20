package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle_search_sessions")
public class VehicleSearchSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IMAGE or TEXT
    @Column(nullable = false, length = 20)
    private String searchType;

    @Column(length = 255)
    private String originalFileName;

    @Column(length = 500)
    private String queryDescription;

    @Column(length = 50)
    private String vehicleType;

    @Column(length = 50)
    private String color;

    private LocalDateTime createdAt;

    private Integer videosScanned;

    private Integer totalMatches;

    @OneToMany(
            mappedBy = "searchSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VehicleSearchMatchEntity> matches =
            new ArrayList<>();

    public VehicleSearchSessionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public void setQueryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVideosScanned() {
        return videosScanned;
    }

    public void setVideosScanned(Integer videosScanned) {
        this.videosScanned = videosScanned;
    }

    public Integer getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(Integer totalMatches) {
        this.totalMatches = totalMatches;
    }

    public List<VehicleSearchMatchEntity> getMatches() {
        return matches;
    }

    public void setMatches(
            List<VehicleSearchMatchEntity> matches
    ) {
        this.matches = matches;
    }
}