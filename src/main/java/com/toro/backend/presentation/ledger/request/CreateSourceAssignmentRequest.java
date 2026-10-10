package com.toro.backend.presentation.ledger.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record CreateSourceAssignmentRequest(
    @NotNull(message = "Source ledger id is required")
    Long sourceLedgerId,

    @NotNull(message = "Organization member id is required")
    Long organizationMemberId,

    @NotNull(message = "Assignment deadline is required")
    @FutureOrPresent(message = "Assignment deadline must be today or in the future")
    LocalDate assignmentDeadline
) {

}
