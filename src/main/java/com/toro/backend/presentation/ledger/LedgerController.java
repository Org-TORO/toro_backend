package com.toro.backend.presentation.ledger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.application.ledger.create_source_assignment.CreateSourceAssignmentUseCase;
import com.toro.backend.application.ledger.create_source_ledger.CreateSourceLedgerUseCase;
import com.toro.backend.application.ledger.get_source_ledgers.GetSourceLedgersUseCase;
import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.mapper.ledger.CreateSourceAssignmentMapper;
import com.toro.backend.infrastructure.mapper.ledger.CreateSourceLedgerMapper;
import com.toro.backend.infrastructure.mapper.ledger.GetSourceLedgersMapper;
import com.toro.backend.presentation.ledger.request.CreateSourceAssignmentRequest;
import com.toro.backend.presentation.ledger.request.CreateSourceLedgerRequest;
import com.toro.backend.presentation.ledger.response.CreateSourceAssignmentResponse;
import com.toro.backend.presentation.ledger.response.CreateSourceLedgerResponse;
import com.toro.backend.presentation.ledger.response.GetSourceLedgersResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ledgers")
@RequiredArgsConstructor
public class LedgerController {

    private final CreateSourceLedgerUseCase createSourceLedgerUseCase;
    private final CreateSourceAssignmentUseCase createSourceAssignmentUseCase;
    private final GetSourceLedgersUseCase getSourceLedgersUseCase;

    @GetMapping("/get-source-ledgers")
    public ResponseEntity<SuccessResponse<List<GetSourceLedgersResponse>>> getSourceLedgers() {
        List<GetSourceLedgersResponse> response = getSourceLedgersUseCase.execute()
            .stream()
            .map(GetSourceLedgersMapper::toResponse)
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Get source ledgers successfully", response)
        );
    }

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

    @PostMapping("/create-source-assignment")
    public ResponseEntity<SuccessResponse<CreateSourceAssignmentResponse>> createSourceAssignment(
        @Valid @RequestBody CreateSourceAssignmentRequest request
    ) {
        CreateSourceAssignmentResponse response = CreateSourceAssignmentMapper.toResponse(
            createSourceAssignmentUseCase.execute(request)
        );

        return ResponseEntity.ok(
            SuccessResponse.success("Create source assignment successfully", response)
        );
    }

}
