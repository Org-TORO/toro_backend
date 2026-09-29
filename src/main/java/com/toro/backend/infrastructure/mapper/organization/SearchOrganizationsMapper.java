package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.search_organizations.SearchOrganizationsResult;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.presentation.organization.response.SearchOrganizationsResponse;

public final class SearchOrganizationsMapper {

    private SearchOrganizationsMapper() {
    }

    public static SearchOrganizationsResult toResult(Organization organization) {
        return new SearchOrganizationsResult(
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

    public static SearchOrganizationsResponse toResponse(SearchOrganizationsResult result) {
        return new SearchOrganizationsResponse(
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
