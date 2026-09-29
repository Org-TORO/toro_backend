package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.get_organization.GetOrganizationResult;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.presentation.organization.response.GetOrganizationResponse;

public final class GetOrganizationMapper {

    private GetOrganizationMapper() {
    }

    public static GetOrganizationResult toResult(Organization organization) {
        return new GetOrganizationResult(
            organization.getId(),
            organization.getOrganizationCode(),
            organization.getOrganizationName(),
            organization.getOrganizationType(),
            organization.getTaxCode(),
            organization.getBlockchainWallet(),
            organization.getCreatedAt(),
            organization.getUpdatedAt()
        );
    }

    public static GetOrganizationResponse toResponse(GetOrganizationResult result) {
        return new GetOrganizationResponse(
            result.id(),
            result.organizationCode(),
            result.organizationName(),
            result.organizationType(),
            result.taxCode(),
            result.blockchainWallet(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
