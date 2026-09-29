package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.get_organizations.GetOrganizationsResult;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.presentation.organization.response.GetOrganizationsResponse;

public final class GetOrganizationsMapper {

    private GetOrganizationsMapper() {
    }

    public static GetOrganizationsResult toResult(Organization organization) {
        return new GetOrganizationsResult(
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

    public static GetOrganizationsResponse toResponse(GetOrganizationsResult result) {
        return new GetOrganizationsResponse(
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
