package com.toro.backend.infrastructure.mapper.organization;

import com.toro.backend.application.organization.add_organization_member.AddOrganizationMemberResult;
import com.toro.backend.infrastructure.database.enums.OrganizationDepartment;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;
import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.organization.request.AddOrganizationMemberRequest;
import com.toro.backend.presentation.organization.response.AddOrganizationMemberResponse;

public final class AddOrganizationMemberMapper {

    private AddOrganizationMemberMapper() {
    }

    public static OrganizationMember toModel(
        AddOrganizationMemberRequest request,
        Organization organization,
        User user
    ) {
        return OrganizationMember.builder()
            .user(user)
            .organization(organization)
            .organizationRole(OrganizationRole.valueOf(request.organizationRole()))
            .organizationDepartment(OrganizationDepartment.valueOf(request.organizationDepartment()))
            .build();
    }

    public static AddOrganizationMemberResult toResult(OrganizationMember organizationMember) {
        User user = organizationMember.getUser();

        return new AddOrganizationMemberResult(
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

    public static AddOrganizationMemberResponse toResponse(AddOrganizationMemberResult result) {
        return new AddOrganizationMemberResponse(
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
