package com.toro.backend.application.organization.get_organizations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.repository.OrganizationRepository;
import com.toro.backend.infrastructure.mapper.organization.GetOrganizationsMapper;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class GetOrganizationsUseCase {

    private final OrganizationRepository organizationRepository;

    public List<GetOrganizationsResult> execute() {
        return organizationRepository.findAll()
            .stream()
            .map(GetOrganizationsMapper::toResult)
            .toList();
    }

}
