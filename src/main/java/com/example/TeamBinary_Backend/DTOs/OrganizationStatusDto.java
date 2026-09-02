package com.example.TeamBinary_Backend.DTOs;

public class OrganizationStatusDto {

    private Boolean active;

    public OrganizationStatusDto() {
    }

    public OrganizationStatusDto(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
