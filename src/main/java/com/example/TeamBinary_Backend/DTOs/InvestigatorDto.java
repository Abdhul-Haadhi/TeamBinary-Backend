package com.example.TeamBinary_Backend.DTOs;

public class InvestigatorDto {

    private Long userId;
    private String email;
    private String role;
    private String organizationId;
    private String organizationCode;
    private String organizationName;

    public InvestigatorDto() {
    }

    public InvestigatorDto(Long userId, String email, String role, String organizationId, String organizationCode, String organizationName) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.organizationId = organizationId;
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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
}
