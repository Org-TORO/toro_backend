package com.toro.backend.presentation.refs.create_source_ledger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.VesselRepository;
import com.toro.backend.infrastructure.exception.BusinessValidationException;
import com.toro.backend.infrastructure.exception.UnauthenticatedException;
import com.toro.backend.infrastructure.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/refs/create-source-ledger")
@RequiredArgsConstructor
public class CreateSourceLedgerRefController {

    private final VesselRepository vesselRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final JwtService jwtService;

    @GetMapping("/refs")
    public ResponseEntity<SuccessResponse<Object>> getRefs(
        Authentication authentication,
        HttpServletRequest request
    ) {
        Organization organization = findCurrentOrganization(authentication, request);

        List<?> vesselRefs = vesselRepository.findAll()
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

        Object response = new Object() {
            public final Long organizationId = organization.getId();
            public final String organizationName = organization.getOrganizationName();
            public final List<?> vessels = vesselRefs;
        };

        return ResponseEntity.ok(
            SuccessResponse.success("Get create source ledger refs successfully", response)
        );
    }

    private Organization findCurrentOrganization(Authentication authentication, HttpServletRequest request) {
        Long userId = getAuthenticatedUserId(authentication, request);

        OrganizationMember organizationMember = organizationMemberRepository
            .findFirstByUserIdOrderByIdAsc(userId)
            .orElseThrow(() -> new BusinessValidationException("Current organization not found."));

        return organizationMember.getOrganization();
    }

    private Long getAuthenticatedUserId(Authentication authentication, HttpServletRequest request) {
        if (authentication == null) {
            throw new UnauthenticatedException("User is not authenticated.");
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return getAuthenticatedUserIdFromToken(request);
        }

        if (principal instanceof Long userId) {
            return userId;
        }

        if (principal instanceof Number userId) {
            return userId.longValue();
        }

        if (principal instanceof String userId) {
            try {
                return Long.parseLong(userId);
            } catch (NumberFormatException ex) {
                throw new UnauthenticatedException("Authenticated user is invalid.");
            }
        }

        throw new UnauthenticatedException("Authenticated user is invalid.");
    }

    private Long getAuthenticatedUserIdFromToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthenticatedException("User is not authenticated.");
        }

        String userId = jwtService.parseClaims(authorization.substring("Bearer ".length())).getSubject();
        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException ex) {
            throw new UnauthenticatedException("Authenticated user is invalid.");
        }
    }

}
