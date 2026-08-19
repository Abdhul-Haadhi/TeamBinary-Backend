package com.example.TeamBinary_Backend.DTOs;


public class VehicleSearchResultDto {

    private Long id;
    private Long detectionId;
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
    private Double plateConfidence;
    private Double ocrConfidence;

    public VehicleSearchResultDto() {
    }

    public VehicleSearchResultDto(Long id, Long detectionId, Long cameraId, String cameraName, String cameraLocation, String videoFileName, String videoUrl, Double matchConfidence, String detectedPlate, String detectedVehicleType, String detectedColor, String timestamp, Double plateConfidence, Double ocrConfidence) {
        this.id = id;
        this.detectionId = detectionId;
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
        this.plateConfidence = plateConfidence;
        this.ocrConfidence = ocrConfidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDetectionId() {
        return detectionId;
    }

    public void setDetectionId(Long detectionId) {
        this.detectionId = detectionId;
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
}
