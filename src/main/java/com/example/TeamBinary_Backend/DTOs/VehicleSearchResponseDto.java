package com.example.TeamBinary_Backend.DTOs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehicleSearchResponseDto {

    private boolean success;

    private String filename;

    private String query;

    private String vehicleType;

    private String color;

    private Double detectionConfidence;

    private Integer videosScanned;

    private String message;

    private Long searchSessionId;

    // Image search uses "matches".
    private List<Map<String, Object>> matches =
            new ArrayList<>();

    // Text search uses "results".
    private List<Map<String, Object>> results =
            new ArrayList<>();

    public VehicleSearchResponseDto() {
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

    public Double getDetectionConfidence() {
        return detectionConfidence;
    }

    public void setDetectionConfidence(
            Double detectionConfidence
    ) {
        this.detectionConfidence = detectionConfidence;
    }

    public Integer getVideosScanned() {
        return videosScanned;
    }

    public void setVideosScanned(Integer videosScanned) {
        this.videosScanned = videosScanned;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSearchSessionId() {
        return searchSessionId;
    }

    public void setSearchSessionId(Long searchSessionId) {
        this.searchSessionId = searchSessionId;
    }

    public List<Map<String, Object>> getMatches() {
        return matches;
    }

    public void setMatches(
            List<Map<String, Object>> matches
    ) {
        this.matches = matches;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(
            List<Map<String, Object>> results
    ) {
        this.results = results;
    }
}