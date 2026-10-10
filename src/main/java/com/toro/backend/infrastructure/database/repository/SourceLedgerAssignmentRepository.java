package com.toro.backend.infrastructure.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toro.backend.infrastructure.database.models.SourceLedgerAssignment;

public interface SourceLedgerAssignmentRepository extends JpaRepository<SourceLedgerAssignment, Long> {

    boolean existsBySourceLedgerIdAndOrganizationMemberId(Long sourceLedgerId, Long organizationMemberId);

}
