package com.toro.backend.application.organization.get_organization;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrganizationUseCase {

    private final GetOrganizationValidator getOrganizationValidator;

    public GetOrganizationResult execute(Long organizationId) {
        Organization organization = getOrganizationValidator.validate(organizationId);

        return GetOrganizationMapper.toResult(organization);
    }

}
