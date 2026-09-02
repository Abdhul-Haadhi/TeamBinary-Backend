package com.example.TeamBinary_Backend.Repositories;


import com.example.TeamBinary_Backend.Entities.CameraNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraNetworkRepository extends JpaRepository<CameraNetwork, Long> {

    // Check if a network name already exists
    boolean existsByNetworkName(String networkName);
}
