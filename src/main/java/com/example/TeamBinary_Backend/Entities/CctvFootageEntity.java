package com.example.TeamBinary_Backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cctv_footages")
public class CctvFootageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_id", nullable = false)
    private CctvCameraEntity camera;

    private String originalFileName;
    private String storedFileName;
    private String filePath;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private String processingStatus;
    private LocalDateTime processingCompletedAt;


    public CctvFootageEntity() {
    }


    public CctvFootageEntity(
            Long id,
            CctvCameraEntity camera,
            String originalFileName,
            String storedFileName,
            String filePath,
            Long fileSize,
            LocalDateTime uploadedAt,
            String processingStatus,
            LocalDateTime processingCompletedAt
    ) {
        this.id = id;
        this.camera = camera;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
        this.processingStatus = processingStatus;
        this.processingCompletedAt = processingCompletedAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public CctvCameraEntity getCamera() {
        return camera;
    }

    public void setCamera(CctvCameraEntity camera) {
        this.camera = camera;
    }


    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }


    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }


    public LocalDateTime getProcessingCompletedAt() {
        return processingCompletedAt;
    }

    public void setProcessingCompletedAt(
            LocalDateTime processingCompletedAt
    ) {
        this.processingCompletedAt = processingCompletedAt;
    }
}