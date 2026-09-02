package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleSearchSessionRepository extends JpaRepository<VehicleSearchSessionEntity, Long> {

}
