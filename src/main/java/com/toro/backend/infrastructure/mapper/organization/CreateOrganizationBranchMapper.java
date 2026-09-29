package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.create_organization_branch.CreateOrganizationBranchResult;
import com.toro.backend.infrastructure.database.enums.BranchType;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.presentation.organization.request.CreateOrganizationBranchRequest;
import com.toro.backend.presentation.organization.response.CreateOrganizationBranchResponse;

public final class CreateOrganizationBranchMapper {

    private CreateOrganizationBranchMapper() {
    }

    public static OrganizationBranch toModel(CreateOrganizationBranchRequest request, Organization organization) {
        return OrganizationBranch.builder()
            .country(request.country())
            .address(request.address())
            .branchType(BranchType.valueOf(request.branchType()))
            .organization(organization)
            .build();
    }

    public static CreateOrganizationBranchResult toResult(OrganizationBranch organizationBranch) {
        return new CreateOrganizationBranchResult(
            organizationBranch.getId(),
            organizationBranch.getCountry(),
            organizationBranch.getAddress(),
            organizationBranch.getBranchType(),
            organizationBranch.getCreatedAt(),
            organizationBranch.getUpdatedAt()
        );
    }

    public static CreateOrganizationBranchResponse toResponse(CreateOrganizationBranchResult result) {
        return new CreateOrganizationBranchResponse(
            result.id(),
            result.country(),
            result.address(),
            result.branchType(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
