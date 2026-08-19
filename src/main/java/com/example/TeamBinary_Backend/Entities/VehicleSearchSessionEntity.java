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
    private String originalFileName;
    private LocalDateTime searchedAt;
    private String status;

    @OneToMany(
            mappedBy = "searchSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VehicleSearchResultEntity> results = new ArrayList<>();

    public VehicleSearchSessionEntity() {
    }

    public VehicleSearchSessionEntity(Long id, String originalFileName, LocalDateTime searchedAt, String status, List<VehicleSearchResultEntity> results) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.searchedAt = searchedAt;
        this.status = status;
        this.results = results;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public LocalDateTime getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(LocalDateTime searchedAt) {
        this.searchedAt = searchedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VehicleSearchResultEntity> getResults() {
        return results;
    }

    public void setResults(List<VehicleSearchResultEntity> results) {
        this.results = results;
    }
}
