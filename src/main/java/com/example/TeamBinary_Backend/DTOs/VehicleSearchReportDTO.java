package com.example.TeamBinary_Backend.DTOs;

public class VehicleSearchReportDTO {
    private Long id;

    private Long searchSessionId;
    private String searchFileName;
    private String searchedAt;
    private String searchStatus;

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

    public VehicleSearchReportDTO() {
    }

    public VehicleSearchReportDTO(
            Long id,
            Long searchSessionId,
            String searchFileName,
            String searchedAt,
            String searchStatus,
            Long cameraId,
            String cameraName,
            String cameraLocation,
            String videoFileName,
            String videoUrl,
            Double matchConfidence,
            String detectedPlate,
            String detectedVehicleType,
            String detectedColor,
            String timestamp
    ) {
        this.id = id;
        this.searchSessionId = searchSessionId;
        this.searchFileName = searchFileName;
        this.searchedAt = searchedAt;
        this.searchStatus = searchStatus;
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSearchSessionId() {
        return searchSessionId;
    }

    public void setSearchSessionId(Long searchSessionId) {
        this.searchSessionId = searchSessionId;
    }

    public String getSearchFileName() {
        return searchFileName;
    }

    public void setSearchFileName(String searchFileName) {
        this.searchFileName = searchFileName;
    }

    public String getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(String searchedAt) {
        this.searchedAt = searchedAt;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
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
}
