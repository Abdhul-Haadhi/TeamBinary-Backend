package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cctv_vehicle_detections")
public class CctvVehicleDetectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footage_id", nullable = false)
    private CctvFootageEntity footage;

    private Integer frameNumber;
    private Double timestampSeconds;
    private String timestamp;
    private String detectedPlate;
    private Double plateConfidence;
    private Double ocrConfidence;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;

    public CctvVehicleDetectionEntity() {
    }

    public CctvVehicleDetectionEntity(Long id, CctvFootageEntity footage, Integer frameNumber, Double timestampSeconds, String timestamp, String detectedPlate, Double plateConfidence, Double ocrConfidence, Double x1, Double y1, Double x2, Double y2) {
        this.id = id;
        this.footage = footage;
        this.frameNumber = frameNumber;
        this.timestampSeconds = timestampSeconds;
        this.timestamp = timestamp;
        this.detectedPlate = detectedPlate;
        this.plateConfidence = plateConfidence;
        this.ocrConfidence = ocrConfidence;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CctvFootageEntity getFootage() {
        return footage;
    }

    public void setFootage(CctvFootageEntity footage) {
        this.footage = footage;
    }

    public Integer getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(Integer frameNumber) {
        this.frameNumber = frameNumber;
    }

    public Double getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(Double timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetectedPlate() {
        return detectedPlate;
    }

    public void setDetectedPlate(String detectedPlate) {
        this.detectedPlate = detectedPlate;
    }

    public Double getPlateConfidence() {
        return plateConfidence;
    }

    public void setPlateConfidence(Double plateConfidence) {
        this.plateConfidence = plateConfidence;
    }

    public Double getOcrConfidence() {
        return ocrConfidence;
    }

    public void setOcrConfidence(Double ocrConfidence) {
        this.ocrConfidence = ocrConfidence;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }
}
