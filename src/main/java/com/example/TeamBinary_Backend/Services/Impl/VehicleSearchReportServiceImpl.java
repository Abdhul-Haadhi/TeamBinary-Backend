package com.example.TeamBinary_Backend.Services.Impl;



import com.example.TeamBinary_Backend.DTOs.VehicleSearchReportDTO;
import com.example.TeamBinary_Backend.Entities.VehicleSearchResultEntity;
import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchResultRepository;
import com.example.TeamBinary_Backend.Repositories.VehicleSearchSessionRepository;
import com.example.TeamBinary_Backend.Services.VehicleSearchReportService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    public class VehicleSearchReportServiceImpl
            implements VehicleSearchReportService {

        private final VehicleSearchResultRepository vehicleSearchResultRepository;
        private final VehicleSearchSessionRepository vehicleSearchSessionRepository;

        public VehicleSearchReportServiceImpl(
                VehicleSearchResultRepository vehicleSearchResultRepository,
                VehicleSearchSessionRepository vehicleSearchSessionRepository
        ) {
            this.vehicleSearchResultRepository = vehicleSearchResultRepository;
            this.vehicleSearchSessionRepository = vehicleSearchSessionRepository;
        }

        @Override
        public List<VehicleSearchReportDTO> getAllVehicleSearchReports() {

            List<VehicleSearchResultEntity> results =
                    vehicleSearchResultRepository.findAllByOrderByIdDesc();

            return results.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }

        @Override
        public List<VehicleSearchReportDTO> getVehicleSearchReportBySession(
                Long sessionId
        ) {

            List<VehicleSearchResultEntity> results =
                    vehicleSearchResultRepository
                            .findBySearchSessionId(sessionId);

            return results.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }

        private VehicleSearchReportDTO convertToDto(
                VehicleSearchResultEntity entity
        ) {

            VehicleSearchReportDTO dto =
                    new VehicleSearchReportDTO();

            dto.setId(entity.getId());

            /*
             * Vehicle Search Session
             */
            if (entity.getSearchSession() != null) {

                VehicleSearchSessionEntity session =
                        entity.getSearchSession();

                dto.setSearchSessionId(session.getId());
                dto.setSearchFileName(session.getOriginalFileName());
                dto.setSearchStatus(session.getStatus());

                LocalDateTime searchedAt =
                        session.getSearchedAt();

                if (searchedAt != null) {
                    dto.setSearchedAt(
                            searchedAt.toString()
                    );
                }
            }

            /*
             * Camera information
             */
            dto.setCameraId(entity.getCameraId());
            dto.setCameraName(entity.getCameraName());
            dto.setCameraLocation(entity.getCameraLocation());

            /*
             * Video information
             */
            dto.setVideoFileName(entity.getVideoFileName());
            dto.setVideoUrl(entity.getVideoUrl());

            /*
             * Vehicle matching information
             */
            dto.setMatchConfidence(entity.getMatchConfidence());
            dto.setDetectedPlate(entity.getDetectedPlate());
            dto.setDetectedVehicleType(
                    entity.getDetectedVehicleType()
            );
            dto.setDetectedColor(entity.getDetectedColor());

            /*
             * Detection timestamp
             */
            dto.setTimestamp(entity.getTimestamp());

            return dto;
        }
    }

