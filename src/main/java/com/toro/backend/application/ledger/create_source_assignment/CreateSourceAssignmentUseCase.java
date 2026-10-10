package com.toro.backend.application.ledger.create_source_assignment;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.SourceLedgerAssignment;
import com.toro.backend.infrastructure.database.repository.SourceLedgerAssignmentRepository;
import com.toro.backend.infrastructure.mapper.ledger.CreateSourceAssignmentMapper;
import com.toro.backend.presentation.ledger.request.CreateSourceAssignmentRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSourceAssignmentUseCase {

    private final CreateSourceAssignmentValidator createSourceAssignmentValidator;
    private final SourceLedgerAssignmentRepository sourceLedgerAssignmentRepository;

    @Transactional
    public CreateSourceAssignmentResult execute(CreateSourceAssignmentRequest request) {
        CreateSourceAssignmentValidationResult validationResult = createSourceAssignmentValidator.validate(request);

        SourceLedgerAssignment sourceLedgerAssignment = CreateSourceAssignmentMapper.toModel(
            request,
            validationResult.sourceLedger(),
            validationResult.organizationMember()
        );

        SourceLedgerAssignment savedSourceLedgerAssignment = sourceLedgerAssignmentRepository.save(
            sourceLedgerAssignment
        );

        return CreateSourceAssignmentMapper.toResult(savedSourceLedgerAssignment);
    }

}
