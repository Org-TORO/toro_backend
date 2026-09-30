package com.toro.backend.application.ledger.create_source_ledger;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.Vessel;

public record CreateSourceLedgerValidationResult(
    Organization organization,
    Vessel vessel
) {

}
