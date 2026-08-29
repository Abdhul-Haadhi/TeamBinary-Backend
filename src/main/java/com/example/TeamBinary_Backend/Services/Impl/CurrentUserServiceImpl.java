package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.Security.OrganizationAwarePrincipal;
import com.example.TeamBinary_Backend.Services.CurrentUserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl
        implements CurrentUserService {


    // =========================================================
    // GET AUTHENTICATION
    // =========================================================

    private Authentication getAuthentication() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (

                authentication == null

                        ||

                        !authentication.isAuthenticated()

        ) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }


        return authentication;
    }


    // =========================================================
    // GET PRINCIPAL
    // =========================================================

    private OrganizationAwarePrincipal getPrincipal() {

        Authentication authentication =
                getAuthentication();


        Object principal =
                authentication.getPrincipal();


        if (
                !(principal
                        instanceof OrganizationAwarePrincipal)
        ) {

            throw new RuntimeException(
                    "Organization information is unavailable"
            );
        }


        return (
                OrganizationAwarePrincipal
                ) principal;
    }


    // =========================================================
    // EMAIL
    // =========================================================

    @Override
    public String getEmail() {

        return getAuthentication()
                .getName();
    }


    // =========================================================
    // ORGANIZATION ID
    // =========================================================

    @Override
    public String getOrganizationId() {

        return getPrincipal()
                .getOrganizationId();
    }


    // =========================================================
    // ORGANIZATION CODE
    // =========================================================

    @Override
    public String getOrganizationCode() {

        return getPrincipal()
                .getOrganizationCode();
    }


    // =========================================================
    // ROLE
    // =========================================================

    @Override
    public String getRole() {

        return getPrincipal()
                .getRole();
    }


    // =========================================================
    // ROLE CHECK
    // =========================================================

    @Override
    public boolean hasRole(String role) {

        if (role == null) {

            return false;
        }

        return role.equalsIgnoreCase(getPrincipal().getRole());
    }
}