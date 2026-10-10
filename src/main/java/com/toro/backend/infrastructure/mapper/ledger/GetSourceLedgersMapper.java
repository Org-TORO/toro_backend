package com.toro.backend.infrastructure.mapper.ledger;

import com.toro.backend.application.ledger.get_source_ledgers.GetSourceLedgersResult;
import com.toro.backend.infrastructure.database.models.SourceLedger;
import com.toro.backend.presentation.ledger.response.GetSourceLedgersResponse;

public final class GetSourceLedgersMapper {

    private GetSourceLedgersMapper() {
    }

    public static GetSourceLedgersResult toResult(SourceLedger sourceLedger) {
        return new GetSourceLedgersResult(
            sourceLedger.getId(),
            sourceLedger.getOrganization().getId(),
            sourceLedger.getVessel().getId(),
            sourceLedger.getSourceType(),
            sourceLedger.getCreatedAt()
        );
    }

    public static GetSourceLedgersResponse toResponse(GetSourceLedgersResult result) {
        return new GetSourceLedgersResponse(
            result.id(),
            result.organizationId(),
            result.vesselId(),
            result.sourceType(),
            result.createdAt()
        );
    }

}
