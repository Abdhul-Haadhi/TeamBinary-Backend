package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detection_sessions")
public class DetectionSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;

    private LocalDateTime detectedAt;

    private Integer totalDetections;

    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PlateDetectionEntity> detections = new ArrayList<>();

    public DetectionSessionEntity() {}

    public DetectionSessionEntity(Long id, String originalFileName, LocalDateTime detectedAt, Integer totalDetections, List<PlateDetectionEntity> detections) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.detectedAt = detectedAt;
        this.totalDetections = totalDetections;
        this.detections = detections;
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

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    public Integer getTotalDetections() {
        return totalDetections;
    }

    public void setTotalDetections(Integer totalDetections) {
        this.totalDetections = totalDetections;
    }

    public List<PlateDetectionEntity> getDetections() {
        return detections;
    }

    public void setDetections(List<PlateDetectionEntity> detections) {
        this.detections = detections;
    }
}
