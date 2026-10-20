package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.VehicleSearchMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleSearchMatchRepository
        extends JpaRepository<VehicleSearchMatchEntity, Long> {

    List<VehicleSearchMatchEntity> findBySearchSessionId(Long sessionId);
}