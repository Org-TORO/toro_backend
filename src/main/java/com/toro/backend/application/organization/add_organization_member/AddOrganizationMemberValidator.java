package com.toro.backend.application.organization.add_organization_member;

import org.springframework.stereotype.Component;

import com.toro.backend.infrastructure.database.models.Organization;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.database.repository.UserRepository;
import com.toro.backend.infrastructure.exception.BusinessValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddOrganizationMemberValidator {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final UserRepository userRepository;

    public AddOrganizationMemberValidationResult validate(Long organizationId, Long userId) {
        Organization organization = organizationRepository.findById(organizationId)
            .orElseThrow(() -> new BusinessValidationException("Organization not found."));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessValidationException("User not found."));

        if (organizationMemberRepository.existsByUserIdAndOrganizationId(userId, organizationId)) {
            throw new BusinessValidationException("User already exists in organization.");
        }

        return new AddOrganizationMemberValidationResult(organization, user);
    }

}
