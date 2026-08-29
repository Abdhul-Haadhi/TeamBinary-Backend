package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CreateOrganizationDto;
import com.example.TeamBinary_Backend.DTOs.OrganizationResponseDto;

import java.util.List;

public interface OrganizationService {

    OrganizationResponseDto createOrganization(CreateOrganizationDto dto);

    List<OrganizationResponseDto> getAllOrganizations();

    OrganizationResponseDto getOrganizationById(String organizationId);

    OrganizationResponseDto updateOrganizationStatus(String organizationId, Boolean active);

}
