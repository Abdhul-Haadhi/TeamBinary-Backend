package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.VehicleClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleClassificationRepository extends JpaRepository<VehicleClassificationEntity, Long> {


}
