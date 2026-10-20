package com.example.TeamBinary_Backend.DTOs;

public class TextVehicleSearchRequestDto {
    private String description;

    public TextVehicleSearchRequestDto() {
    }

    public TextVehicleSearchRequestDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
