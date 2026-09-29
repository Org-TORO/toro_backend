package com.toro.backend.infrastructure.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.toro.backend.infrastructure.database.models.OrganizationMember;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {

    boolean existsByUserIdAndOrganizationId(Long userId, Long organizationId);

    @EntityGraph(attributePaths = "organization")
    Optional<OrganizationMember> findFirstByUserIdOrderByIdAsc(Long userId);

}
