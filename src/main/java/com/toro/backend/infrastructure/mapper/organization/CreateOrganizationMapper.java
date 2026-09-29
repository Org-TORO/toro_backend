package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.create_organization.CreateOrganizationResult;
import com.toro.backend.infrastructure.database.enums.OrganizationType;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.presentation.organization.request.CreateOrganizationRequest;
import com.toro.backend.presentation.organization.response.CreateOrganizationResponse;

public final class CreateOrganizationMapper {

    private CreateOrganizationMapper() {
    }

    public static Organization toModel(CreateOrganizationRequest request) {
        return Organization.builder()
            .organizationCode(request.organizationCode())
            .organizationName(request.organizationName())
            .organizationType(OrganizationType.valueOf(request.organizationType()))
            .taxCode(request.taxCode())
            .blockchainWallet(request.blockchainWallet())
            .build();
    }

    public static CreateOrganizationResult toResult(Organization organization) {
        return new CreateOrganizationResult(
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

    public static CreateOrganizationResponse toResponse(CreateOrganizationResult result) {
        return new CreateOrganizationResponse(
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
