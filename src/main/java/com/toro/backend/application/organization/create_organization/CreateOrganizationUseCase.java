package com.toro.backend.application.organization.create_organization;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.mapper.organization.CreateOrganizationMapper;
import com.toro.backend.presentation.organization.request.CreateOrganizationRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrganizationUseCase {

    private final CreateOrganizationValidator createOrganizationValidator;
    private final OrganizationRepository organizationRepository;


    @Transactional
    public CreateOrganizationResult execute(CreateOrganizationRequest request) {
        createOrganizationValidator.validate(request);

        Organization organization = CreateOrganizationMapper.toModel(request);

        Organization savedOrganization = organizationRepository.save(organization);

        return CreateOrganizationMapper.toResult(savedOrganization);
    }

}
