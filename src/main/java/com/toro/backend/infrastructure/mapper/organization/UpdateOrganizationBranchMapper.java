package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.update_organization_branch.UpdateOrganizationBranchResult;
import com.toro.backend.infrastructure.database.enums.BranchType;
import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.presentation.organization.request.UpdateOrganizationBranchRequest;
import com.toro.backend.presentation.organization.response.UpdateOrganizationBranchResponse;

public final class UpdateOrganizationBranchMapper {

    private UpdateOrganizationBranchMapper() {
    }

    public static OrganizationBranch toModel(
        UpdateOrganizationBranchRequest request,
        OrganizationBranch organizationBranch
    ) {
        organizationBranch.setCountry(request.country());
        organizationBranch.setAddress(request.address());
        organizationBranch.setBranchType(BranchType.valueOf(request.branchType()));

        return organizationBranch;
    }

    public static UpdateOrganizationBranchResult toResult(OrganizationBranch organizationBranch) {
        return new UpdateOrganizationBranchResult(
            organizationBranch.getId(),
            organizationBranch.getCountry(),
            organizationBranch.getAddress(),
            organizationBranch.getBranchType(),
            organizationBranch.getCreatedAt(),
            organizationBranch.getUpdatedAt()
        );
    }

    public static UpdateOrganizationBranchResponse toResponse(UpdateOrganizationBranchResult result) {
        return new UpdateOrganizationBranchResponse(
            result.id(),
            result.country(),
            result.address(),
            result.branchType(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
