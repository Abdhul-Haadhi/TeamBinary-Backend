package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.CreateOrganizationDto;
import com.example.TeamBinary_Backend.DTOs.OrganizationResponseDto;
import com.example.TeamBinary_Backend.Entities.OrganizationEntity;
import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.OrganizationRepository;
import com.example.TeamBinary_Backend.Repositories.UserRepository;
import com.example.TeamBinary_Backend.Services.OrganizationService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public OrganizationResponseDto createOrganization(CreateOrganizationDto dto) {


        if (dto.getOrganizationCode() == null || dto.getOrganizationCode().trim().isEmpty()) {
            throw new RuntimeException("Organization code is required");
        }


        if (dto.getOrganizationName() == null || dto.getOrganizationName().trim().isEmpty()) {
            throw new RuntimeException("Organization name is required");
        }


        if (dto.getAdminEmail() == null || dto.getAdminEmail().trim().isEmpty()) {
            throw new RuntimeException("Administrator email is required");
        }


        if (dto.getAdminPassword() == null || dto.getAdminPassword().length() < 6) {
            throw new RuntimeException("Administrator password must contain at least 6 characters");
        }

        String organizationCode = dto.getOrganizationCode().trim().toUpperCase();


        String adminEmail = dto.getAdminEmail().trim().toLowerCase();



        // Check duplicate organization code
        if (organizationRepository.existsByOrganizationCode(organizationCode)) {
            throw new RuntimeException("Organization code already exists");
        }



        // Create organization
        OrganizationEntity organization = new OrganizationEntity();

        organization.setOrganizationCode(organizationCode);
        organization.setOrganizationName(dto.getOrganizationName().trim());
        organization.setAddress(dto.getAddress() == null ? null : dto.getAddress().trim());
        organization.setActive(true);

        OrganizationEntity savedOrganization = organizationRepository.save(organization);

        // Create initial ADMIN
        UserEntity admin = new UserEntity();

        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(dto.getAdminPassword()));
        admin.setRole("ADMIN");
        admin.setOrganization(savedOrganization);

        try {userRepository.save(admin);

        }
        catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Administrator email already exists for this organization");
        }

        return toDto(
                savedOrganization
        );
    }

    // GET ALL ORGANIZATIONS
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationResponseDto> getAllOrganizations() {

        return organizationRepository
                .findAll()
                .stream()
                .map(
                        this::toDto
                )
                .toList();
    }


    // GET ONE ORGANIZATION
    @Override
    @Transactional(readOnly = true)
    public OrganizationResponseDto getOrganizationById(String organizationId) {

        OrganizationEntity organization = organizationRepository.findById(organizationId)
                        .orElseThrow(() -> new RuntimeException("Organization not found"));

        return toDto(organization);
    }

    // UPDATE STATUS
    @Override
    @Transactional
    public OrganizationResponseDto updateOrganizationStatus(String organizationId, Boolean active) {

        if (active == null) {
            throw new RuntimeException("Active status is required");
        }


        OrganizationEntity organization = organizationRepository.findById(organizationId).orElseThrow(
                                () -> new RuntimeException("Organization not found"));

        organization.setActive(active);


        OrganizationEntity saved = organizationRepository.save(organization);

        return toDto(saved);
    }


    // ENTITY -> DTO
    private OrganizationResponseDto toDto(OrganizationEntity organization) {

        long totalUsers = userRepository.findByOrganizationId(organization.getId()).size();

        return new OrganizationResponseDto(

                organization.getId(),
                organization.getOrganizationCode(),
                organization.getOrganizationName(),
                organization.getAddress(),
                organization.getActive(),
                totalUsers
        );
    }

}
