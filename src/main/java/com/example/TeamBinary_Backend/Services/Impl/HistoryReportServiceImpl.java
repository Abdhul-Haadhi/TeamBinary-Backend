package com.example.TeamBinary_Backend.Services.Impl;


import com.example.TeamBinary_Backend.DTOs.HistoryReportDTO;
import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchSessionRepository;
import com.example.TeamBinary_Backend.Services.HistoryReportService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HistoryReportServiceImpl implements HistoryReportService {

    private final VehicleSearchSessionRepository vehicleSearchSessionRepository;

    public HistoryReportServiceImpl(
            VehicleSearchSessionRepository vehicleSearchSessionRepository
    ) {
        this.vehicleSearchSessionRepository =
                vehicleSearchSessionRepository;
    }

    @Override
    public List<HistoryReportDTO> getAllHistoryReports() {

        List<VehicleSearchSessionEntity> sessions =
                vehicleSearchSessionRepository.findAll();

        return sessions.stream()
                .map(session -> new HistoryReportDTO(
                        session.getId(),
                        session.getOriginalFileName(),
                        session.getSearchedAt() != null
                                ? session.getSearchedAt().toString()
                                : null,
                        session.getStatus()
                ))
                .collect(Collectors.toList());
    }
}

