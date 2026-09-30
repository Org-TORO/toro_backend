package com.toro.backend.presentation.ledger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.application.ledger.create_source_ledger.CreateSourceLedgerUseCase;
import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.mapper.ledger.CreateSourceLedgerMapper;
import com.toro.backend.presentation.ledger.request.CreateSourceLedgerRequest;
import com.toro.backend.presentation.ledger.response.CreateSourceLedgerResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ledgers")
@RequiredArgsConstructor
public class LedgerController {

    private final CreateSourceLedgerUseCase createSourceLedgerUseCase;

    @PostMapping("/create-source-ledger")
    public ResponseEntity<SuccessResponse<CreateSourceLedgerResponse>> createSourceLedger(
        @Valid @RequestBody CreateSourceLedgerRequest request
    ) {
        CreateSourceLedgerResponse response = CreateSourceLedgerMapper.toResponse(
            createSourceLedgerUseCase.execute(request)
        );

        return ResponseEntity.ok(
            SuccessResponse.success("Create source ledger successfully", response)
        );
    }

}
