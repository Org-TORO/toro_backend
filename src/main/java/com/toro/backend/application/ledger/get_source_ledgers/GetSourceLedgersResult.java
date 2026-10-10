package com.toro.backend.application.ledger.get_source_ledgers;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.SourceType;

public record GetSourceLedgersResult(
    Long id,
    Long organizationId,
    Long vesselId,
    SourceType sourceType,
    Instant createdAt
) {

}
