package com.toro.backend.infrastructure.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.toro.backend.infrastructure.database.models.SourceLedger;

public interface SourceLedgerRepository extends JpaRepository<SourceLedger, Long> {

    boolean existsByVesselId(Long vesselId);

    @EntityGraph(attributePaths = {"organization", "vessel"})
    @Query("select sourceLedger from SourceLedger sourceLedger")
    List<SourceLedger> findAllWithOrganizationAndVessel();

}
