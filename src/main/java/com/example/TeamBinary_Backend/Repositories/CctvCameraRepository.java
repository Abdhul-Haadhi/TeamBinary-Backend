package com.example.TeamBinary_Backend.Repositories;


import com.example.TeamBinary_Backend.Entities.CctvCameraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CctvCameraRepository
        extends JpaRepository<CctvCameraEntity, Long> {

    List<CctvCameraEntity> findByStatus(String status);
}


