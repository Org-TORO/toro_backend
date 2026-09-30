package com.toro.backend.application.ledger.create_source_ledger;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.SourceType;

public record CreateSourceLedgerResult(
    Long id,
    Long organizationId,
    Long vesselId,
    SourceType sourceType,
    Instant createdAt
) {

}
