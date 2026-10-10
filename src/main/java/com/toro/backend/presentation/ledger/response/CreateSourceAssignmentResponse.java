package com.toro.backend.presentation.ledger.response;

import java.time.Instant;
import java.time.LocalDate;

import com.toro.backend.infrastructure.database.enums.AssignmentStatus;

public record CreateSourceAssignmentResponse(
    Long id,
    Long sourceLedgerId,
    Long organizationMemberId,
    LocalDate assignmentDeadline,
    AssignmentStatus assignmentStatus,
    Instant createdAt
) {

}
