package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.update_organization.UpdateOrganizationResult;
import com.toro.backend.infrastructure.database.enums.OrganizationType;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.presentation.organization.request.UpdateOrganizationRequest;
import com.toro.backend.presentation.organization.response.UpdateOrganizationResponse;

public final class UpdateOrganizationMapper {

    private UpdateOrganizationMapper() {
    }

    public static Organization toModel(UpdateOrganizationRequest request, Organization organization) {
        organization.setOrganizationCode(request.organizationCode());
        organization.setOrganizationName(request.organizationName());
        organization.setOrganizationType(OrganizationType.valueOf(request.organizationType()));
        organization.setTaxCode(request.taxCode());
        organization.setBlockchainWallet(request.blockchainWallet());

        return organization;
    }

    public static UpdateOrganizationResult toResult(Organization organization) {
        return new UpdateOrganizationResult(
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

    public static UpdateOrganizationResponse toResponse(UpdateOrganizationResult result) {
        return new UpdateOrganizationResponse(
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
