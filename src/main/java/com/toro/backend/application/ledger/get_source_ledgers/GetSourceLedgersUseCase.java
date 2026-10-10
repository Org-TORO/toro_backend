package com.toro.backend.application.ledger.get_source_ledgers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toro.backend.infrastructure.database.repository.SourceLedgerRepository;
import com.toro.backend.infrastructure.mapper.ledger.GetSourceLedgersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetSourceLedgersUseCase {

    private final SourceLedgerRepository sourceLedgerRepository;

    @Transactional(readOnly = true)
    public List<GetSourceLedgersResult> execute() {
        return sourceLedgerRepository.findAllWithOrganizationAndVessel()
            .stream()
            .map(GetSourceLedgersMapper::toResult)
            .toList();
    }

}
