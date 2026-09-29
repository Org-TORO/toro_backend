package com.toro.backend.infrastructure.database.models;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.toro.backend.infrastructure.database.enums.AssignmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source_ledger_assignments")
public class SourceLedgerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_deadline", nullable = false)
    private LocalDate assignmentDeadline;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false)
    @ColumnDefault("'PENDING'")
    private AssignmentStatus assignmentStatus = AssignmentStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    // Relationships
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_ledger_id", nullable = false)
    private SourceLedger sourceLedger;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_member_id", nullable = false)
    private OrganizationMember organizationMember;

    @PrePersist
    protected void onCreate() {
        if (assignmentStatus == null) {
            assignmentStatus = AssignmentStatus.PENDING;
        }

        createdAt = Instant.now();
    }

}
