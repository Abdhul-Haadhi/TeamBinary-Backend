package com.example.TeamBinary_Backend.Services;

import com.example.TeamBinary_Backend.DTOs.VehicleSearchReportDTO;
import com.example.TeamBinary_Backend.DTOs.VehicleSearchReportDTO;

import java.util.List;

public interface VehicleSearchReportService {

    List<VehicleSearchReportDTO> getAllVehicleSearchReports();

    List<VehicleSearchReportDTO> getVehicleSearchReportBySession(Long sessionId);
}
