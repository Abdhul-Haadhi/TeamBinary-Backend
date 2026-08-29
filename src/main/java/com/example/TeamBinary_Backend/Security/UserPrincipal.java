package com.example.TeamBinary_Backend.Security;

import com.example.TeamBinary_Backend.Entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal
        implements UserDetails, OrganizationAwarePrincipal {

    private final UserEntity user;


    public UserPrincipal(UserEntity user) {

        this.user = user;
    }


    // =========================================================
    // AUTHORITIES
    // =========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()
                )
        );
    }


    // =========================================================
    // PASSWORD
    // =========================================================

    @Override
    public String getPassword() {

        return user.getPassword();
    }


    // =========================================================
    // USERNAME
    // =========================================================

    @Override
    public String getUsername() {

        return user.getEmail();
    }


    // =========================================================
    // USER INFORMATION
    // =========================================================

    public Long getUserId() {

        return user.getUserId();
    }


    @Override
    public String getRole() {

        return user.getRole();
    }


    // =========================================================
    // ORGANIZATION INFORMATION
    // =========================================================

    @Override
    public String getOrganizationId() {

        if (user.getOrganization() == null) {

            throw new IllegalStateException(
                    "User organization is not configured"
            );
        }

        return user.getOrganization().getId();
    }


    @Override
    public String getOrganizationCode() {

        if (user.getOrganization() == null) {

            throw new IllegalStateException(
                    "User organization is not configured"
            );
        }

        return user
                .getOrganization()
                .getOrganizationCode();
    }


    public String getOrganizationName() {

        if (user.getOrganization() == null) {

            throw new IllegalStateException(
                    "User organization is not configured"
            );
        }

        return user
                .getOrganization()
                .getOrganizationName();
    }


    // =========================================================
    // ACCOUNT STATUS
    // =========================================================

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }


    @Override
    public boolean isAccountNonLocked() {

        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }


    @Override
    public boolean isEnabled() {

        return true;
    }
}