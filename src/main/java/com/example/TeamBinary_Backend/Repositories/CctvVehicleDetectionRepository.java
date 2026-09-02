package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.CctvVehicleDetectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CctvVehicleDetectionRepository extends JpaRepository<CctvVehicleDetectionEntity, Long> {

    List<CctvVehicleDetectionEntity> findByFootageIdOrderByTimestampSecondsAsc(Long footageId);

    List<CctvVehicleDetectionEntity> findByDetectedPlateIgnoreCase(String detectedPlate);
}
