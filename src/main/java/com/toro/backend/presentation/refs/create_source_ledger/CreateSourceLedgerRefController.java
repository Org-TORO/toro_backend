package com.toro.backend.presentation.refs.create_source_ledger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.VesselRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/refs/create-source-ledger")
@RequiredArgsConstructor
public class CreateSourceLedgerRefController {

    private final VesselRepository vesselRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    @GetMapping("/get-vessels")
    public ResponseEntity<SuccessResponse<List<?>>> getVessels() {
        List<?> response = vesselRepository.findAll()
            .stream()
            .map(vessel -> new Object() {
                public final Long id = vessel.getId();
                public final String registrationNumber = vessel.getRegistrationNumber();
                public final String fishingMethod = vessel.getFishingMethod();
                public final String captainName = vessel.getCaptainName();
                public final String fishingLicense = vessel.getFishingLicense();
                public final String imoNumber = vessel.getImoNumber();
            })
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Get vessels successfully", response)
        );
    }

    @GetMapping("/get-main-staffs")
    public ResponseEntity<SuccessResponse<List<?>>> getMainStaffs(
        @RequestParam(value = "search", required = false) String search
    ) {
        List<?> response = organizationMemberRepository.searchByOrganizationRoleAndUser(
                OrganizationRole.MAIN_STAFF,
                normalizeSearchValue(search)
            )
            .stream()
            .map(member -> new Object() {
                public final Long id = member.getId();
                public final Long userId = member.getUser().getId();
                public final String fullName = member.getUser().getFullName();
                public final String email = member.getUser().getEmail();
                public final String phoneNumber = member.getUser().getPhoneNumber();
                public final Long organizationId = member.getOrganization().getId();
                public final String organizationName = member.getOrganization().getOrganizationName();
            })
            .toList();

        return ResponseEntity.ok(
            SuccessResponse.success("Get main staffs successfully", response)
        );
    }

    private String normalizeSearchValue(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        return value.trim();
    }

}
