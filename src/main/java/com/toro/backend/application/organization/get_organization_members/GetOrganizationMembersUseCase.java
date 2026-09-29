package com.toro.backend.application.organization.get_organization_members;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationMembersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrganizationMembersUseCase {

    private final GetOrganizationMembersValidator getOrganizationMembersValidator;

    @Transactional(readOnly = true)
    public List<GetOrganizationMembersResult> execute(Long organizationBranchId) {
        OrganizationBranch organizationBranch = getOrganizationMembersValidator.validate(organizationBranchId);

        return organizationBranch.getOrganizationMembers()
            .stream()
            .map(GetOrganizationMembersMapper::toResult)
            .toList();
    }

}
