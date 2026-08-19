package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.PlateDetectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateDetectionRepository extends JpaRepository<PlateDetectionEntity, Long> {

    List<PlateDetectionEntity> findBySessionId(Long sessionId);
}
