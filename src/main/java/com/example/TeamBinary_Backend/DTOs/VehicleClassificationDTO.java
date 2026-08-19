package com.example.TeamBinary_Backend.DTOs;

public class VehicleClassificationDTO {

    private Long id;

    private String vehicleType;

    private String confidence;

    private String camera;

    private String detectedTime;

    private String imagePath;

    public VehicleClassificationDTO() {
    }

    public VehicleClassificationDTO(Long id, String vehicleType, String confidence, String camera, String detectedTime, String imagePath) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.confidence = confidence;
        this.camera = camera;
        this.detectedTime = detectedTime;
        this.imagePath = imagePath;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getDetectedTime() {
        return detectedTime;
    }

    public void setDetectedTime(String detectedTime) {
        this.detectedTime = detectedTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

