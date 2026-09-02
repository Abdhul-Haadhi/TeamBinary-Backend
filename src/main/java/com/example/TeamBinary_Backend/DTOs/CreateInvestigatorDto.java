package com.example.TeamBinary_Backend.DTOs;

public class CreateInvestigatorDto {

    private String email;
    private String password;

    public CreateInvestigatorDto() {
    }

    public CreateInvestigatorDto(String email, String password) {
        this.email = email;
        this.password = password;
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
