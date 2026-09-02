package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.CctvFootageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CctvFootageRepository extends JpaRepository<CctvFootageEntity, Long> {

    List<CctvFootageEntity> findByCameraIdOrderByUploadedAtDesc(Long cameraId);

    List<CctvFootageEntity> findByProcessingStatus(String processingStatus);
}
