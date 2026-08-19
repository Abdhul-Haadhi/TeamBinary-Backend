package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.VehicleSearchResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleSearchResultRepository extends JpaRepository<VehicleSearchResultEntity, Long> {

    List<VehicleSearchResultEntity> findBySearchSessionId(Long searchSessionId);

}
