package com.toro.backend.presentation.ledger.request;

import com.toro.backend.infrastructure.database.enums.SourceType;
import com.toro.backend.infrastructure.validator.EnumExist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSourceLedgerRequest(
    @NotNull(message = "Organization id is required")
    Long organizationId,
    @NotNull(message = "Vessel id is required")
    Long vesselId,
    @NotBlank(message = "Source type is required")
    @EnumExist(enumClass = SourceType.class, message = "Source type does not exist.")
    String sourceType
) {

}
