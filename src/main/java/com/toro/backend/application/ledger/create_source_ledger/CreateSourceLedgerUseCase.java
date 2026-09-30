package com.toro.backend.application.ledger.create_source_ledger;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.SourceLedger;
import com.toro.backend.infrastructure.database.repository.SourceLedgerRepository;
import com.toro.backend.infrastructure.mapper.ledger.CreateSourceLedgerMapper;
import com.toro.backend.presentation.ledger.request.CreateSourceLedgerRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSourceLedgerUseCase {

    private final CreateSourceLedgerValidator createSourceLedgerValidator;
    private final SourceLedgerRepository sourceLedgerRepository;

    @Transactional
    public CreateSourceLedgerResult execute(CreateSourceLedgerRequest request) {
        CreateSourceLedgerValidationResult validationResult = createSourceLedgerValidator.validate(request);

        SourceLedger sourceLedger = CreateSourceLedgerMapper.toModel(
            request,
            validationResult.organization(),
            validationResult.vessel()
        );

        SourceLedger savedSourceLedger = sourceLedgerRepository.save(sourceLedger);

        return CreateSourceLedgerMapper.toResult(savedSourceLedger);
    }

}
