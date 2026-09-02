package com.example.TeamBinary_Backend.DTOs;

import java.util.List;

public class CctvVideoProcessingResponseDto {

    private boolean success;
    private String filename;
    private Integer framesProcessed;
    private List<DetectionDto> detections;

    public static class DetectionDto{
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


        public DetectionDto() {
        }

        public DetectionDto(Integer frameNumber, Double timestampSeconds, String timestamp, String detectedPlate, Double plateConfidence, Double ocrConfidence, Double x1, Double y1, Double x2, Double y2) {
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

    public CctvVideoProcessingResponseDto() {
    }

    public CctvVideoProcessingResponseDto(boolean success, String filename, Integer framesProcessed, List<DetectionDto> detections) {
        this.success = success;
        this.filename = filename;
        this.framesProcessed = framesProcessed;
        this.detections = detections;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getFramesProcessed() {
        return framesProcessed;
    }

    public void setFramesProcessed(Integer framesProcessed) {
        this.framesProcessed = framesProcessed;
    }

    public List<DetectionDto> getDetections() {
        return detections;
    }

    public void setDetections(List<DetectionDto> detections) {
        this.detections = detections;
    }
}
