package com.toro.backend.infrastructure.mapper.ledger;

import com.toro.backend.application.ledger.create_source_assignment.CreateSourceAssignmentResult;
import com.toro.backend.infrastructure.database.enums.AssignmentStatus;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.SourceLedger;
import com.toro.backend.infrastructure.database.models.SourceLedgerAssignment;
import com.toro.backend.presentation.ledger.request.CreateSourceAssignmentRequest;
import com.toro.backend.presentation.ledger.response.CreateSourceAssignmentResponse;

public final class CreateSourceAssignmentMapper {

    private CreateSourceAssignmentMapper() {
    }

    public static SourceLedgerAssignment toModel(
        CreateSourceAssignmentRequest request,
        SourceLedger sourceLedger,
        OrganizationMember organizationMember
    ) {
        return SourceLedgerAssignment.builder()
            .sourceLedger(sourceLedger)
            .organizationMember(organizationMember)
            .assignmentDeadline(request.assignmentDeadline())
            .assignmentStatus(AssignmentStatus.PENDING)
            .build();
    }

    public static CreateSourceAssignmentResult toResult(SourceLedgerAssignment sourceLedgerAssignment) {
        return new CreateSourceAssignmentResult(
            sourceLedgerAssignment.getId(),
            sourceLedgerAssignment.getSourceLedger().getId(),
            sourceLedgerAssignment.getOrganizationMember().getId(),
            sourceLedgerAssignment.getAssignmentDeadline(),
            sourceLedgerAssignment.getAssignmentStatus(),
            sourceLedgerAssignment.getCreatedAt()
        );
    }

    public static CreateSourceAssignmentResponse toResponse(CreateSourceAssignmentResult result) {
        return new CreateSourceAssignmentResponse(
            result.id(),
            result.sourceLedgerId(),
            result.organizationMemberId(),
            result.assignmentDeadline(),
            result.assignmentStatus(),
            result.createdAt()
        );
    }

}
