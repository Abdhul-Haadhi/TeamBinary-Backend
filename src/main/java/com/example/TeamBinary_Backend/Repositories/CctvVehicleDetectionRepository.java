//package com.example.TeamBinary_Backend.Repositories;
//
//import com.example.TeamBinary_Backend.Entities.CctvVehicleDetectionEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CctvVehicleDetectionRepository extends JpaRepository<CctvVehicleDetectionEntity, Long> {
//
//    List<CctvVehicleDetectionEntity> findByFootageIdOrderByTimestampSecondsAsc(Long footageId);
//
//    List<CctvVehicleDetectionEntity> findByDetectedPlateIgnoreCase(String detectedPlate);
//}


package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.CctvVehicleDetectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CctvVehicleDetectionRepository
        extends JpaRepository<CctvVehicleDetectionEntity, Long> {

    List<CctvVehicleDetectionEntity>
    findByFootageIdOrderByTimestampSecondsAsc(Long footageId);

    @Query("""
        SELECT d
        FROM CctvVehicleDetectionEntity d
        WHERE REPLACE(
                  REPLACE(
                      REPLACE(UPPER(d.detectedPlate), '-', ''),
                      ' ', ''
                  ),
                  '.', ''
              ) = UPPER(:plate)
    """)
    List<CctvVehicleDetectionEntity> findByNormalizedDetectedPlate(
            @Param("plate") String plate
    );
}