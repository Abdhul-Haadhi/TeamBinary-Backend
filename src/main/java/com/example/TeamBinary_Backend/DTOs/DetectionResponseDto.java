package com.example.TeamBinary_Backend.DTOs;

import java.util.List;

public class DetectionResponseDto {

    private boolean success;
    private String filename;
    private List<DetectionDto> detections;
    private String image;
    private Long sessionId;

    public static class DetectionDto {

        private String className;
        private double confidence;
        private double x1;
        private double y1;
        private double x2;
        private double y2;

        // OCR fields
        private String recognizedText;
        private double ocrConfidence;

        public DetectionDto() {
        }

        public DetectionDto(String className, double confidence, double x1, double y1, double x2, double y2, String recognizedText, double ocrConfidence) {

            this.className = className;
            this.confidence = confidence;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.recognizedText = recognizedText;
            this.ocrConfidence = ocrConfidence;
        }


        public String getClassName() {
            return className;
        }
        public void setClassName(String className) {
            this.className = className;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public double getX1() {
            return x1;
        }

        public void setX1(double x1) {
            this.x1 = x1;
        }

        public double getY1() {
            return y1;
        }

        public void setY1(double y1) {
            this.y1 = y1;
        }

        public double getX2() {
            return x2;
        }

        public void setX2(double x2) {
            this.x2 = x2;
        }

        public double getY2() {
            return y2;
        }

        public void setY2(double y2) {
            this.y2 = y2;
        }

        public String getRecognizedText() {
            return recognizedText;
        }

        public void setRecognizedText(String recognizedText) {
            this.recognizedText = recognizedText;
        }

        public double getOcrConfidence() {
            return ocrConfidence;
        }

        public void setOcrConfidence(double ocrConfidence) {
            this.ocrConfidence = ocrConfidence;
        }
    }

    public DetectionResponseDto() {
    }

    public DetectionResponseDto(boolean success, String filename, List<DetectionDto> detections, String image, Long sessionId) {
        this.success = success;
        this.filename = filename;
        this.detections = detections;
        this.image = image;
        this.sessionId = sessionId;
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

    public List<DetectionDto> getDetections() {
        return detections;
    }

    public void setDetections(List<DetectionDto> detections) {
        this.detections = detections;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}