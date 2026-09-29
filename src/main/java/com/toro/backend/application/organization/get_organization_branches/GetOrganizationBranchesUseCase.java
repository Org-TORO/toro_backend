package com.toro.backend.application.organization.get_organization_branches;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationBranchesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrganizationBranchesUseCase {

    private final GetOrganizationBranchesValidator getOrganizationBranchesValidator;

    @Transactional(readOnly = true)
    public List<GetOrganizationBranchesResult> execute(Long organizationId) {
        Organization organization = getOrganizationBranchesValidator.validate(organizationId);

        return organization.getBranches()
            .stream()
            .map(GetOrganizationBranchesMapper::toResult)
            .toList();
    }

}
