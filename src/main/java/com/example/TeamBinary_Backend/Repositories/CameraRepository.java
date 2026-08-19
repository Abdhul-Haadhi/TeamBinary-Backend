package com.example.TeamBinary_Backend.Repositories;

import java.util.List;

import com.example.TeamBinary_Backend.Entities.Camera;
import com.example.TeamBinary_Backend.Enum.CameraStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    // All cameras in a network
    List<Camera> findByNetwork_NetworkId(Long networkId);

    // All cameras by status
    List<Camera> findByStatus(CameraStatus status);

    // Check IP already exists
    boolean existsByIpAddress(String ipAddress);

    // Count cameras in a network
    int countByNetwork_NetworkId(Long networkId);

    // Count active cameras in a network
    int countByNetwork_NetworkIdAndStatus(Long networkId, CameraStatus status);
}
