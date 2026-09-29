package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.update_organization_member.UpdateOrganizationMemberResult;
import com.toro.backend.infrastructure.database.enums.OrganizationDepartment;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.organization.request.UpdateOrganizationMemberRequest;
import com.toro.backend.presentation.organization.response.UpdateOrganizationMemberResponse;

public final class UpdateOrganizationMemberMapper {

    private UpdateOrganizationMemberMapper() {
    }

    public static OrganizationMember toModel(
        UpdateOrganizationMemberRequest request,
        OrganizationMember organizationMember
    ) {
        organizationMember.setOrganizationRole(OrganizationRole.valueOf(request.organizationRole()));
        organizationMember.setOrganizationDepartment(OrganizationDepartment.valueOf(request.organizationDepartment()));

        return organizationMember;
    }

    public static UpdateOrganizationMemberResult toResult(OrganizationMember organizationMember) {
        User user = organizationMember.getUser();

        return new UpdateOrganizationMemberResult(
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

    public static UpdateOrganizationMemberResponse toResponse(UpdateOrganizationMemberResult result) {
        return new UpdateOrganizationMemberResponse(
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
