package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.get_organization_branches.GetOrganizationBranchesResult;
import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.presentation.organization.response.GetOrganizationBranchesResponse;

public final class GetOrganizationBranchesMapper {

    private GetOrganizationBranchesMapper() {
    }

    public static GetOrganizationBranchesResult toResult(OrganizationBranch organizationBranch) {
        return new GetOrganizationBranchesResult(
            organizationBranch.getId(),
            organizationBranch.getCountry(),
            organizationBranch.getAddress(),
            organizationBranch.getBranchType(),
            organizationBranch.getCreatedAt(),
            organizationBranch.getUpdatedAt()
        );
    }

    public static GetOrganizationBranchesResponse toResponse(GetOrganizationBranchesResult result) {
        return new GetOrganizationBranchesResponse(
            result.id(),
            result.country(),
            result.address(),
            result.branchType(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
