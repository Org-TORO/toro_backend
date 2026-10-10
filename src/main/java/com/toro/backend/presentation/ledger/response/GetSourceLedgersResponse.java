package com.toro.backend.presentation.ledger.response;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.SourceType;

public record GetSourceLedgersResponse(
    Long id,
    Long organizationId,
    Long vesselId,
    SourceType sourceType,
    Instant createdAt
) {

}
