package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.get_organization_branch.GetOrganizationBranchResult;
import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.presentation.organization.response.GetOrganizationBranchResponse;

public final class GetOrganizationBranchMapper {

    private GetOrganizationBranchMapper() {
    }

    public static GetOrganizationBranchResult toResult(OrganizationBranch organizationBranch) {
        return new GetOrganizationBranchResult(
            organizationBranch.getId(),
            organizationBranch.getCountry(),
            organizationBranch.getAddress(),
            organizationBranch.getBranchType(),
            organizationBranch.getCreatedAt(),
            organizationBranch.getUpdatedAt()
        );
    }

    public static GetOrganizationBranchResponse toResponse(GetOrganizationBranchResult result) {
        return new GetOrganizationBranchResponse(
            result.id(),
            result.country(),
            result.address(),
            result.branchType(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
