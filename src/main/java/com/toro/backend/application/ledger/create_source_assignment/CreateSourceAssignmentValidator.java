package com.toro.backend.application.ledger.create_source_assignment;

import org.springframework.stereotype.Component;

import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.SourceLedger;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.SourceLedgerAssignmentRepository;
import com.toro.backend.infrastructure.database.repository.SourceLedgerRepository;
import com.toro.backend.infrastructure.exception.BusinessValidationException;
import com.toro.backend.presentation.ledger.request.CreateSourceAssignmentRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateSourceAssignmentValidator {

    private final SourceLedgerRepository sourceLedgerRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final SourceLedgerAssignmentRepository sourceLedgerAssignmentRepository;

    public CreateSourceAssignmentValidationResult validate(CreateSourceAssignmentRequest request) {
        SourceLedger sourceLedger = sourceLedgerRepository.findById(request.sourceLedgerId())
            .orElseThrow(() -> new BusinessValidationException("Source ledger not found."));

        OrganizationMember organizationMember = organizationMemberRepository.findById(request.organizationMemberId())
            .orElseThrow(() -> new BusinessValidationException("Organization member not found."));

        if (!sourceLedger.getOrganization().getId().equals(organizationMember.getOrganization().getId())) {
            throw new BusinessValidationException("Organization member does not belong to source ledger organization.");
        }

        if (sourceLedgerAssignmentRepository.existsBySourceLedgerIdAndOrganizationMemberId(
            request.sourceLedgerId(),
            request.organizationMemberId()
        )) {
            throw new BusinessValidationException("Source assignment already exists.");
        }

        return new CreateSourceAssignmentValidationResult(sourceLedger, organizationMember);
    }

}
