package com.example.TeamBinary_Backend.Security;

import com.example.TeamBinary_Backend.DTOs.LoginResponseDto;
import com.example.TeamBinary_Backend.DTOs.UserDto;
import com.example.TeamBinary_Backend.Entities.OrganizationEntity;
import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.OrganizationRepository;
import com.example.TeamBinary_Backend.Repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl
        implements AuthService {


    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public AuthServiceImpl(

            UserRepository userRepository,

            OrganizationRepository organizationRepository,

            PasswordEncoder passwordEncoder,

            JwtService jwtService

    ) {

        this.userRepository =
                userRepository;

        this.organizationRepository =
                organizationRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.jwtService =
                jwtService;
    }


    @Override
    public LoginResponseDto login(
            UserDto userDto
    ) {


        // =====================================================
        // VALIDATE INPUT
        // =====================================================

        if (
                userDto == null
        ) {

            throw new RuntimeException(
                    "Login information is required"
            );
        }


        if (
                userDto.getOrganizationCode()
                        == null

                        ||

                        userDto.getOrganizationCode()
                                .trim()
                                .isEmpty()

        ) {

            throw new RuntimeException(
                    "Organization code is required"
            );
        }


        if (
                userDto.getEmail()
                        == null

                        ||

                        userDto.getEmail()
                                .trim()
                                .isEmpty()

        ) {

            throw new RuntimeException(
                    "Email is required"
            );
        }


        if (
                userDto.getPassword()
                        == null

                        ||

                        userDto.getPassword()
                                .isEmpty()

        ) {

            throw new RuntimeException(
                    "Password is required"
            );
        }


        String organizationCode =
                userDto
                        .getOrganizationCode()
                        .trim();


        String email =
                userDto
                        .getEmail()
                        .trim()
                        .toLowerCase();


        // =====================================================
        // FIND ORGANIZATION
        // =====================================================

        OrganizationEntity organization =

                organizationRepository
                        .findByOrganizationCode(
                                organizationCode
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Invalid organization code"
                                        )
                        );


        // =====================================================
        // CHECK ORGANIZATION STATUS
        // =====================================================

        if (
                !Boolean.TRUE.equals(
                        organization.getActive()
                )
        ) {

            throw new RuntimeException(
                    "This organization is inactive"
            );
        }


        // =====================================================
        // FIND USER INSIDE ORGANIZATION
        // =====================================================

        UserEntity user =

                userRepository
                        .findByEmailAndOrganizationId(
                                email,
                                organization.getId()
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Invalid email or password"
                                        )
                        );


        // =====================================================
        // PASSWORD
        // =====================================================

        if (
                !passwordEncoder.matches(
                        userDto.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        // =====================================================
        // ROLE
        // =====================================================

        if (
                user.getRole() == null

                        ||

                        user.getRole().isBlank()

        ) {

            throw new RuntimeException(
                    "User role is not configured"
            );
        }


        // =====================================================
        // JWT
        // =====================================================

        String token =
                jwtService.generateToken(

                        user.getEmail(),

                        user.getRole(),

                        organization.getId(),

                        organization.getOrganizationCode()

                );


        // =====================================================
        // RESPONSE
        // =====================================================

        return new LoginResponseDto(

                user.getUserId(),

                user.getEmail(),

                user.getRole(),

                organization.getId(),

                organization.getOrganizationCode(),

                organization.getOrganizationName(),

                token

        );
    }
}