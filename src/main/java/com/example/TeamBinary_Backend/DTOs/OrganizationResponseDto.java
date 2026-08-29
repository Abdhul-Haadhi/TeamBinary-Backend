package com.example.TeamBinary_Backend.DTOs;

public class OrganizationResponseDto {

    private String id;
    private String organizationCode;
    private String organizationName;
    private String address;
    private Boolean active;
    private Long totalUsers;

    public OrganizationResponseDto() {
    }

    public OrganizationResponseDto(String id, String organizationCode, String organizationName, String address, Boolean active, Long totalUsers) {
        this.id = id;
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
        this.address = address;
        this.active = active;
        this.totalUsers = totalUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }
}
