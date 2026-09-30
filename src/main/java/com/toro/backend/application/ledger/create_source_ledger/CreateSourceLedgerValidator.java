package com.toro.backend.application.ledger.create_source_ledger;

import org.springframework.stereotype.Component;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.Vessel;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.database.repository.SourceLedgerRepository;
import com.toro.backend.infrastructure.database.repository.VesselRepository;
import com.toro.backend.infrastructure.exception.BusinessValidationException;
import com.toro.backend.presentation.ledger.request.CreateSourceLedgerRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateSourceLedgerValidator {

    private final OrganizationRepository organizationRepository;
    private final VesselRepository vesselRepository;
    private final SourceLedgerRepository sourceLedgerRepository;

    public CreateSourceLedgerValidationResult validate(CreateSourceLedgerRequest request) {
        Organization organization = organizationRepository.findById(request.organizationId())
            .orElseThrow(() -> new BusinessValidationException("Organization not found."));

        Vessel vessel = vesselRepository.findById(request.vesselId())
            .orElseThrow(() -> new BusinessValidationException("Vessel not found."));

        if (sourceLedgerRepository.existsByVesselId(request.vesselId())) {
            throw new BusinessValidationException("Source ledger already exists.");
        }

        return new CreateSourceLedgerValidationResult(organization, vessel);
    }

}
