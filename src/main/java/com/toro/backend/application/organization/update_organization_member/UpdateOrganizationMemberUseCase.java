package com.toro.backend.application.organization.update_organization_member;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.mapper.organization.UpdateOrganizationMemberMapper;
import com.toro.backend.presentation.organization.request.UpdateOrganizationMemberRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateOrganizationMemberUseCase {

    private final UpdateOrganizationMemberValidator updateOrganizationMemberValidator;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Transactional
    public UpdateOrganizationMemberResult execute(Long memberId, UpdateOrganizationMemberRequest request) {
        OrganizationMember organizationMember = updateOrganizationMemberValidator.validate(memberId);

        UpdateOrganizationMemberMapper.toModel(request, organizationMember);

        OrganizationMember savedOrganizationMember = organizationMemberRepository.save(organizationMember);

        return UpdateOrganizationMemberMapper.toResult(savedOrganizationMember);
    }

}
