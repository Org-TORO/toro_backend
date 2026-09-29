package com.toro.backend.application.organization.update_organization;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.mapper.organization.UpdateOrganizationMapper;
import com.toro.backend.presentation.organization.request.UpdateOrganizationRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateOrganizationUseCase {

    private final UpdateOrganizationValidator updateOrganizationValidator;
    private final OrganizationRepository organizationRepository;


    @Transactional
    public UpdateOrganizationResult execute(Long organizationId, UpdateOrganizationRequest request) {
        Organization organization = updateOrganizationValidator.validate(organizationId, request);

        UpdateOrganizationMapper.toModel(request, organization);

        Organization savedOrganization = organizationRepository.save(organization);

        return UpdateOrganizationMapper.toResult(savedOrganization);
    }

}
