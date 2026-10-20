package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_search_matches")
public class VehicleSearchMatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_session_id", nullable = false)
    private VehicleSearchSessionEntity searchSession;

    private String cameraName;

    private String cameraLocation;

    private String videoFileName;

    private Integer frameNumber;

    private String timestamp;

    private Double timestampSeconds;

    private String vehicleType;

    private String color;

    private String className;

    private String detectedPlate;

    private Double plateConfidence;

    private Double ocrConfidence;

    private Double confidence;

    private Double matchConfidence;

    private Double visualSimilarity;

    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;

    public VehicleSearchMatchEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleSearchSessionEntity getSearchSession() {
        return searchSession;
    }

    public void setSearchSession(
            VehicleSearchSessionEntity searchSession
    ) {
        this.searchSession = searchSession;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraLocation() {
        return cameraLocation;
    }

    public void setCameraLocation(String cameraLocation) {
        this.cameraLocation = cameraLocation;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public Integer getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(Integer frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(Double timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Double getMatchConfidence() {
        return matchConfidence;
    }

    public void setMatchConfidence(Double matchConfidence) {
        this.matchConfidence = matchConfidence;
    }

    public Double getVisualSimilarity() {
        return visualSimilarity;
    }

    public void setVisualSimilarity(Double visualSimilarity) {
        this.visualSimilarity = visualSimilarity;
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