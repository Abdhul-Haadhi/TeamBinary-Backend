package com.example.TeamBinary_Backend.DTOs;

public class CreateOrganizationDto {

    private String organizationCode;
    private String organizationName;
    private String address;
    private String adminEmail;
    private String adminPassword;

    public CreateOrganizationDto() {
    }

    public CreateOrganizationDto(String organizationCode, String organizationName, String address, String adminEmail, String adminPassword) {
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
        this.address = address;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
