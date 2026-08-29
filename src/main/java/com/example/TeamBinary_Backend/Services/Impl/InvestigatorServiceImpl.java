package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CreateInvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.InvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.UpdateInvestigatorDto;
import com.example.TeamBinary_Backend.Entities.OrganizationEntity;
import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.OrganizationRepository;
import com.example.TeamBinary_Backend.Repositories.UserRepository;
import com.example.TeamBinary_Backend.Services.CurrentUserService;
import com.example.TeamBinary_Backend.Services.InvestigatorService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestigatorServiceImpl
        implements InvestigatorService {


    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final PasswordEncoder passwordEncoder;

    private final CurrentUserService currentUserService;


    public InvestigatorServiceImpl(
            UserRepository userRepository,
            OrganizationRepository organizationRepository,
            PasswordEncoder passwordEncoder,
            CurrentUserService currentUserService
    ) {

        this.userRepository =
                userRepository;

        this.organizationRepository =
                organizationRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.currentUserService =
                currentUserService;
    }


    // =========================================================
    // CREATE INVESTIGATOR
    // =========================================================

    @Override
    @Transactional
    public InvestigatorDto createInvestigator(
            CreateInvestigatorDto dto
    ) {

        // -----------------------------------------------------
        // Only ADMIN
        // -----------------------------------------------------

        requireAdmin();


        if (dto == null) {

            throw new RuntimeException(
                    "Investigator information is required"
            );
        }


        // -----------------------------------------------------
        // Validate email
        // -----------------------------------------------------

        String email =
                dto.getEmail() == null
                        ? ""
                        : dto.getEmail()
                        .trim()
                        .toLowerCase();


        if (email.isBlank()) {

            throw new RuntimeException(
                    "Investigator email is required"
            );
        }


        // -----------------------------------------------------
        // Validate password
        // -----------------------------------------------------

        if (
                dto.getPassword() == null
                        ||
                        dto.getPassword().length() < 6
        ) {

            throw new RuntimeException(
                    "Password must contain at least 6 characters"
            );
        }


        // -----------------------------------------------------
        // Current organization
        // -----------------------------------------------------

        String organizationId =
                currentUserService.getOrganizationId();


        OrganizationEntity organization =
                getActiveOrganization(
                        organizationId
                );


        // -----------------------------------------------------
        // Duplicate email within organization
        // -----------------------------------------------------

        if (
                userRepository.existsByEmailAndOrganizationId(
                        email,
                        organizationId
                )
        ) {

            throw new RuntimeException(
                    "An account with this email already exists in this organization"
            );
        }


        // -----------------------------------------------------
        // Create investigator
        // -----------------------------------------------------

        UserEntity investigator =
                new UserEntity();


        investigator.setEmail(
                email
        );


        investigator.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );


        investigator.setRole(
                "INVESTIGATOR"
        );


        investigator.setOrganization(
                organization
        );


        UserEntity saved =
                userRepository.save(
                        investigator
                );


        return toDto(saved);
    }


    // =========================================================
    // GET ALL INVESTIGATORS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<InvestigatorDto> getMyOrganizationInvestigators() {

        requireAdmin();

        String organizationId = currentUserService.getOrganizationId();

        // Make sure organization still exists and is active
        getActiveOrganization(organizationId);


        return userRepository.findByOrganizationIdAndRole(organizationId, "INVESTIGATOR")
                .stream()
                .map(this::toDto)

                .toList();
    }


    // =========================================================
    // GET ONE INVESTIGATOR
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public InvestigatorDto getInvestigatorById(Long userId) {

        requireAdmin();

        if (userId == null) {
            throw new RuntimeException("Investigator ID is required");
        }

        String organizationId = currentUserService.getOrganizationId();

        getActiveOrganization(organizationId);

        UserEntity investigator = userRepository.findByUserIdAndOrganizationIdAndRole(userId, organizationId, "INVESTIGATOR")
                        .orElseThrow(() -> new RuntimeException("Investigator not found in your organization"));


        return toDto(investigator);
    }


    // =========================================================
    // UPDATE INVESTIGATOR
    // =========================================================

    @Override
    @Transactional
    public InvestigatorDto updateInvestigator(
            Long userId,
            UpdateInvestigatorDto dto
    ) {

        requireAdmin();


        if (userId == null) {

            throw new RuntimeException(
                    "Investigator ID is required"
            );
        }


        if (dto == null) {

            throw new RuntimeException(
                    "Update information is required"
            );
        }


        String organizationId =
                currentUserService.getOrganizationId();


        getActiveOrganization(
                organizationId
        );


        // -----------------------------------------------------
        // IMPORTANT:
        // Query is already restricted to:
        //
        // userId + organizationId + INVESTIGATOR
        // -----------------------------------------------------

        UserEntity investigator =
                userRepository
                        .findByUserIdAndOrganizationIdAndRole(
                                userId,
                                organizationId,
                                "INVESTIGATOR"
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Investigator not found in your organization"
                                        )
                        );


        // -----------------------------------------------------
        // EMAIL
        // -----------------------------------------------------

        if (
                dto.getEmail() != null
                        &&
                        !dto.getEmail()
                                .trim()
                                .isEmpty()
        ) {

            String newEmail =
                    dto.getEmail()
                            .trim()
                            .toLowerCase();


            if (
                    !newEmail.equals(
                            investigator.getEmail()
                    )

                            &&

                            userRepository
                                    .existsByEmailAndOrganizationId(
                                            newEmail,
                                            organizationId
                                    )
            ) {

                throw new RuntimeException(
                        "Another account with this email already exists in this organization"
                );
            }


            investigator.setEmail(
                    newEmail
            );
        }


        // -----------------------------------------------------
        // PASSWORD
        // -----------------------------------------------------

        if (
                dto.getPassword() != null
                        &&
                        !dto.getPassword().isBlank()
        ) {

            if (
                    dto.getPassword().length() < 6
            ) {

                throw new RuntimeException(
                        "Password must contain at least 6 characters"
                );
            }


            investigator.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );
        }


        UserEntity saved =
                userRepository.save(
                        investigator
                );


        return toDto(
                saved
        );
    }


    // =========================================================
    // DELETE INVESTIGATOR
    // =========================================================

    @Override
    @Transactional
    public void deleteInvestigator(
            Long userId
    ) {

        requireAdmin();


        if (userId == null) {

            throw new RuntimeException(
                    "Investigator ID is required"
            );
        }


        String organizationId =
                currentUserService.getOrganizationId();


        getActiveOrganization(
                organizationId
        );


        // -----------------------------------------------------
        // Again, query is restricted by:
        //
        // user + organization + role
        // -----------------------------------------------------

        UserEntity investigator =
                userRepository
                        .findByUserIdAndOrganizationIdAndRole(
                                userId,
                                organizationId,
                                "INVESTIGATOR"
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Investigator not found in your organization"
                                        )
                        );


        userRepository.delete(
                investigator
        );
    }


    // =========================================================
    // REQUIRE ADMIN
    // =========================================================

    private void requireAdmin() {

        if (
                !currentUserService.hasRole(
                        "ADMIN"
                )
        ) {

            throw new RuntimeException(
                    "Only organization administrators can manage investigators"
            );
        }
    }


    // =========================================================
    // GET ACTIVE ORGANIZATION
    // =========================================================

    private OrganizationEntity getActiveOrganization(
            String organizationId
    ) {

        OrganizationEntity organization =
                organizationRepository
                        .findById(
                                organizationId
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Organization not found"
                                        )
                        );


        if (
                !Boolean.TRUE.equals(
                        organization.getActive()
                )
        ) {

            throw new RuntimeException(
                    "This organization is inactive"
            );
        }


        return organization;
    }


    // =========================================================
    // ENTITY -> DTO
    // =========================================================

    private InvestigatorDto toDto(
            UserEntity user
    ) {

        OrganizationEntity organization =
                user.getOrganization();


        return new InvestigatorDto(

                user.getUserId(),

                user.getEmail(),

                user.getRole(),

                organization != null
                        ? organization.getId()
                        : null,

                organization != null
                        ? organization.getOrganizationCode()
                        : null,

                organization != null
                        ? organization.getOrganizationName()
                        : null
        );
    }
}