package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {

    Optional<OrganizationEntity> findByOrganizationCode(String organizationCode);
    boolean existsByOrganizationCode(String organizationCode);
}
