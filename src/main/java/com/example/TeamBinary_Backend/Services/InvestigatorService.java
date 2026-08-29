package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.CreateInvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.InvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.UpdateInvestigatorDto;

import java.util.List;

public interface InvestigatorService {

    InvestigatorDto createInvestigator(CreateInvestigatorDto dto);
    List<InvestigatorDto> getMyOrganizationInvestigators();
    InvestigatorDto getInvestigatorById(Long userId);
    InvestigatorDto updateInvestigator(Long userId, UpdateInvestigatorDto dto);
    void deleteInvestigator(Long userId);

}
