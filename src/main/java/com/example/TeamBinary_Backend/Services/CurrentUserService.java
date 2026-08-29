package com.example.TeamBinary_Backend.Services;

public interface CurrentUserService {

    String getEmail();

    String getOrganizationId();

    String getOrganizationCode();

    String getRole();

    boolean hasRole(String role);
}
