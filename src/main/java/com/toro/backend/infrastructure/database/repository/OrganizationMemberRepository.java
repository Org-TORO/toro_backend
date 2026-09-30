package com.toro.backend.infrastructure.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toro.backend.infrastructure.database.enums.OrganizationRole;
import com.toro.backend.infrastructure.database.models.OrganizationMember;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {

    boolean existsByUserIdAndOrganizationId(Long userId, Long organizationId);

    @EntityGraph(attributePaths = "organization")
    Optional<OrganizationMember> findFirstByUserIdOrderByIdAsc(Long userId);

    @EntityGraph(attributePaths = {"user", "organization"})
    List<OrganizationMember> findByOrganizationRole(OrganizationRole organizationRole);

    @EntityGraph(attributePaths = {"user", "organization"})
    @Query("""
        SELECT organizationMember
        FROM OrganizationMember organizationMember
        JOIN organizationMember.user user
        WHERE organizationMember.organizationRole = :organizationRole
        AND (
            :search = '' 
            OR LOWER(user.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(user.email) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    List<OrganizationMember> searchByOrganizationRoleAndUser(
        @Param("organizationRole") OrganizationRole organizationRole,
        @Param("search") String search
    );

}
