package com.toro.backend.application.organization.get_organization_branch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationBranchMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrganizationBranchUseCase {

    private final GetOrganizationBranchValidator getOrganizationBranchValidator;

    @Transactional(readOnly = true)
    public GetOrganizationBranchResult execute(Long organizationBranchId) {
        OrganizationBranch organizationBranch = getOrganizationBranchValidator.validate(organizationBranchId);

        return GetOrganizationBranchMapper.toResult(organizationBranch);
    }

}
