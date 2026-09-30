package com.toro.backend.infrastructure.mapper.ledger;

import com.toro.backend.application.ledger.create_source_ledger.CreateSourceLedgerResult;
import com.toro.backend.infrastructure.database.enums.SourceType;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.SourceLedger;
import com.toro.backend.infrastructure.database.models.Vessel;
import com.toro.backend.presentation.ledger.request.CreateSourceLedgerRequest;
import com.toro.backend.presentation.ledger.response.CreateSourceLedgerResponse;

public final class CreateSourceLedgerMapper {

    private CreateSourceLedgerMapper() {
    }

    public static SourceLedger toModel(
        CreateSourceLedgerRequest request,
        Organization organization,
        Vessel vessel
    ) {
        return SourceLedger.builder()
            .organization(organization)
            .vessel(vessel)
            .sourceType(SourceType.valueOf(request.sourceType()))
            .build();
    }

    public static CreateSourceLedgerResult toResult(SourceLedger sourceLedger) {
        return new CreateSourceLedgerResult(
            sourceLedger.getId(),
            sourceLedger.getOrganization().getId(),
            sourceLedger.getVessel().getId(),
            sourceLedger.getSourceType(),
            sourceLedger.getCreatedAt()
        );
    }

    public static CreateSourceLedgerResponse toResponse(CreateSourceLedgerResult result) {
        return new CreateSourceLedgerResponse(
            result.id(),
            result.organizationId(),
            result.vesselId(),
            result.sourceType(),
            result.createdAt()
        );
    }

}
