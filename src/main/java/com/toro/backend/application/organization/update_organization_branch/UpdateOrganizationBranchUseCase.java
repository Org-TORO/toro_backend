package com.toro.backend.application.organization.update_organization_branch;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.infrastructure.database.repository.OrganizationBranchRepository;
import com.toro.backend.infrastructure.mapper.organization.UpdateOrganizationBranchMapper;
import com.toro.backend.presentation.organization.request.UpdateOrganizationBranchRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateOrganizationBranchUseCase {

    private final UpdateOrganizationBranchValidator updateOrganizationBranchValidator;
    private final OrganizationBranchRepository organizationBranchRepository;


    @Transactional
    public UpdateOrganizationBranchResult execute(
        Long organizationBranchId,
        UpdateOrganizationBranchRequest request
    ) {
        OrganizationBranch organizationBranch = updateOrganizationBranchValidator.validate(
            organizationBranchId
        );

        UpdateOrganizationBranchMapper.toModel(request, organizationBranch);

        OrganizationBranch savedOrganizationBranch = organizationBranchRepository.save(organizationBranch);

        return UpdateOrganizationBranchMapper.toResult(savedOrganizationBranch);
    }

}
