package com.example.TeamBinary_Backend.DTOs;

public class UserDto {

    private String organizationCode;
    private String email;
    private String password;

    public UserDto() {
    }

    public UserDto(String organizationCode, String email, String password) {
        this.organizationCode = organizationCode;
        this.email = email;
        this.password = password;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
