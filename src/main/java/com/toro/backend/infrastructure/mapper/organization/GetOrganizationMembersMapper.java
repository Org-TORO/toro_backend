package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.get_organization_members.GetOrganizationMembersResult;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.organization.response.GetOrganizationMembersResponse;

public final class GetOrganizationMembersMapper {

    private GetOrganizationMembersMapper() {
    }

    public static GetOrganizationMembersResult toResult(OrganizationMember organizationMember) {
        User user = organizationMember.getUser();

        return new GetOrganizationMembersResult(
            user.getId(),
            organizationMember.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            organizationMember.getOrganizationRole(),
            organizationMember.getOrganizationDepartment(),
            organizationMember.getCreatedAt(),
            organizationMember.getUpdatedAt()
        );
    }

    public static GetOrganizationMembersResponse toResponse(GetOrganizationMembersResult result) {
        return new GetOrganizationMembersResponse(
            result.userId(),
            result.memberId(),
            result.fullName(),
            result.email(),
            result.phoneNumber(),
            result.organizationRole(),
            result.organizationDepartment(),
            result.createdAt(),
            result.updatedAt()
        );
    }

}
