package com.toro.backend.application.organization.add_organization_member;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.mapper.organization.AddOrganizationMemberMapper;
import com.toro.backend.presentation.organization.request.AddOrganizationMemberRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddOrganizationMemberUseCase {

    private final AddOrganizationMemberValidator addOrganizationMemberValidator;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Transactional
    public AddOrganizationMemberResult execute(Long organizationId, AddOrganizationMemberRequest request) {
        AddOrganizationMemberValidationResult validationResult = addOrganizationMemberValidator.validate(
            organizationId,
            request.userId()
        );

        OrganizationMember organizationMember = AddOrganizationMemberMapper.toModel(
            request,
            validationResult.organization(),
            validationResult.user()
        );

        OrganizationMember savedOrganizationMember = organizationMemberRepository.save(organizationMember);

        return AddOrganizationMemberMapper.toResult(savedOrganizationMember);
    }

}
