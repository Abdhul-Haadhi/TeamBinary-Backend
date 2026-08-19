package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_search_results")
public class VehicleSearchResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detection_id")
    private CctvVehicleDetectionEntity detection;

    private Long cameraId;
    private String cameraName;
    private String cameraLocation;

    private String videoFileName;
    private String videoUrl;

    private Double matchConfidence;
    private String detectedPlate;
    private String detectedVehicleType;
    private String detectedColor;
    private String timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_session_id")
    private VehicleSearchSessionEntity searchSession;


    public VehicleSearchResultEntity() {
    }

    public VehicleSearchResultEntity(Long id, CctvVehicleDetectionEntity detection, Long cameraId, String cameraName, String cameraLocation, String videoFileName, String videoUrl, Double matchConfidence, String detectedPlate, String detectedVehicleType, String detectedColor, String timestamp, VehicleSearchSessionEntity searchSession) {
        this.id = id;
        this.detection = detection;
        this.cameraId = cameraId;
        this.cameraName = cameraName;
        this.cameraLocation = cameraLocation;
        this.videoFileName = videoFileName;
        this.videoUrl = videoUrl;
        this.matchConfidence = matchConfidence;
        this.detectedPlate = detectedPlate;
        this.detectedVehicleType = detectedVehicleType;
        this.detectedColor = detectedColor;
        this.timestamp = timestamp;
        this.searchSession = searchSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CctvVehicleDetectionEntity getDetection() {
        return detection;
    }

    public void setDetection(CctvVehicleDetectionEntity detection) {
        this.detection = detection;
    }

    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Double getMatchConfidence() {
        return matchConfidence;
    }

    public void setMatchConfidence(Double matchConfidence) {
        this.matchConfidence = matchConfidence;
    }

    public String getDetectedPlate() {
        return detectedPlate;
    }

    public void setDetectedPlate(String detectedPlate) {
        this.detectedPlate = detectedPlate;
    }

    public String getDetectedVehicleType() {
        return detectedVehicleType;
    }

    public void setDetectedVehicleType(String detectedVehicleType) {
        this.detectedVehicleType = detectedVehicleType;
    }

    public String getDetectedColor() {
        return detectedColor;
    }

    public void setDetectedColor(String detectedColor) {
        this.detectedColor = detectedColor;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public VehicleSearchSessionEntity getSearchSession() {
        return searchSession;
    }

    public void setSearchSession(VehicleSearchSessionEntity searchSession) {
        this.searchSession = searchSession;
    }
}
