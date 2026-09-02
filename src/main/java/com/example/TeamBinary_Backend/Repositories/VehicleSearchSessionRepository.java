package com.example.TeamBinary_Backend.Repositories;



import com.example.TeamBinary_Backend.Entities.VehicleSearchSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleSearchSessionRepository
        extends JpaRepository<VehicleSearchSessionEntity, Long> {
}
