package com.example.TeamBinary_Backend.Security;


public interface OrganizationAwarePrincipal {

    String getOrganizationId();

    String getOrganizationCode();

    String getRole();
}
