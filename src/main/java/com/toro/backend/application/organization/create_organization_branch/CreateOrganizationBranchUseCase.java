package com.toro.backend.application.organization.create_organization_branch;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.OrganizationBranch;
import com.toro.backend.infrastructure.database.repository.OrganizationBranchRepository;
import com.toro.backend.infrastructure.mapper.organization.CreateOrganizationBranchMapper;
import com.toro.backend.presentation.organization.request.CreateOrganizationBranchRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrganizationBranchUseCase {

    private final CreateOrganizationBranchValidator createOrganizationBranchValidator;
    private final OrganizationBranchRepository organizationBranchRepository;


    @Transactional
    public CreateOrganizationBranchResult execute(Long organizationId, CreateOrganizationBranchRequest request) {
        Organization organization = createOrganizationBranchValidator.validate(organizationId);

        OrganizationBranch organizationBranch = CreateOrganizationBranchMapper.toModel(request, organization);

        OrganizationBranch savedOrganizationBranch = organizationBranchRepository.save(organizationBranch);

        return CreateOrganizationBranchMapper.toResult(savedOrganizationBranch);
    }

}
