package com.toro.backend.application.ledger.create_source_assignment;

import java.time.Instant;
import java.time.LocalDate;

import com.toro.backend.infrastructure.database.enums.AssignmentStatus;

public record CreateSourceAssignmentResult(
    Long id,
    Long sourceLedgerId,
    Long organizationMemberId,
    LocalDate assignmentDeadline,
    AssignmentStatus assignmentStatus,
    Instant createdAt
) {

}
