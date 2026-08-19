package com.example.TeamBinary_Backend.DTOs;

public class CctvCameraDto {

    private Long id;
    private String cameraName;
    private String location;
    private String cameraType;
    private String description;
    private String cameraUrl;
    private String status;


    public CctvCameraDto() {
    }

    public CctvCameraDto(Long id, String cameraName, String location, String cameraType, String description, String cameraUrl, String status) {
        this.id = id;
        this.cameraName = cameraName;
        this.location = location;
        this.cameraType = cameraType;
        this.description = description;
        this.cameraUrl = cameraUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCameraUrl() {
        return cameraUrl;
    }

    public void setCameraUrl(String cameraUrl) {
        this.cameraUrl = cameraUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
