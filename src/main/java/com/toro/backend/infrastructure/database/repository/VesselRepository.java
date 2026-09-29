package com.toro.backend.infrastructure.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toro.backend.infrastructure.database.models.Vessel;

public interface VesselRepository extends JpaRepository<Vessel, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);

}
