package com.example.TeamBinary_Backend.DTOs;

import java.util.List;

public class VehicleSearchResponseDto {

    private boolean success;
    private Long searchSessionId;
    private String filename;
    private String detectedPlate;
    private Double queryOcrConfidence;
    private String status;
    private List<VehicleSearchResultDto> results;

    public VehicleSearchResponseDto() {
    }

    public VehicleSearchResponseDto(boolean success, Long searchSessionId, String filename, String detectedPlate, Double queryOcrConfidence, String status, List<VehicleSearchResultDto> results) {
        this.success = success;
        this.searchSessionId = searchSessionId;
        this.filename = filename;
        this.detectedPlate = detectedPlate;
        this.queryOcrConfidence = queryOcrConfidence;
        this.status = status;
        this.results = results;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getSearchSessionId() {
        return searchSessionId;
    }

    public void setSearchSessionId(Long searchSessionId) {
        this.searchSessionId = searchSessionId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDetectedPlate() {
        return detectedPlate;
    }

    public void setDetectedPlate(String detectedPlate) {
        this.detectedPlate = detectedPlate;
    }

    public Double getQueryOcrConfidence() {
        return queryOcrConfidence;
    }

    public void setQueryOcrConfidence(Double queryOcrConfidence) {
        this.queryOcrConfidence = queryOcrConfidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VehicleSearchResultDto> getResults() {
        return results;
    }

    public void setResults(List<VehicleSearchResultDto> results) {
        this.results = results;
    }
}
