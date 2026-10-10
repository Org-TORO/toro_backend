package com.toro.backend.application.ledger.create_source_assignment;

import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.SourceLedger;

public record CreateSourceAssignmentValidationResult(
    SourceLedger sourceLedger,
    OrganizationMember organizationMember
) {

}
