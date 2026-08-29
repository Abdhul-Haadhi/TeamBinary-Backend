package com.example.TeamBinary_Backend.Repositories;

import com.example.TeamBinary_Backend.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // =========================================================
    // AUTHENTICATION
    // =========================================================

    Optional<UserEntity> findByEmailAndOrganizationId(
            String email,
            String organizationId
    );


    // =========================================================
    // ORGANIZATION USERS
    // =========================================================

    List<UserEntity> findByOrganizationId(
            String organizationId
    );


    // =========================================================
    // ORGANIZATION INVESTIGATORS
    // =========================================================

    List<UserEntity> findByOrganizationIdAndRole(
            String organizationId,
            String role
    );


    // =========================================================
    // FIND ONE USER IN ONE ORGANIZATION
    // =========================================================

    Optional<UserEntity> findByUserIdAndOrganizationId(
            Long userId,
            String organizationId
    );


    // =========================================================
    // FIND INVESTIGATOR IN ONE ORGANIZATION
    // =========================================================

    Optional<UserEntity> findByUserIdAndOrganizationIdAndRole(
            Long userId,
            String organizationId,
            String role
    );


    // =========================================================
    // DUPLICATE CHECKS
    // =========================================================

    boolean existsByEmailAndOrganizationId(
            String email,
            String organizationId
    );


    boolean existsByEmail(
            String email
    );
}