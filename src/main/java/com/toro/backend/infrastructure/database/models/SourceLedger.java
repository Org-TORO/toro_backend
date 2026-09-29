package com.toro.backend.infrastructure.database.models;

import java.time.Instant;
import java.util.List;

import com.toro.backend.infrastructure.database.enums.SourceType;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "source_ledgers")
public class SourceLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private SourceType sourceType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    // Relationships
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vessel_id", nullable = false, unique = true)
    private Vessel vessel;

    @OneToMany(
        mappedBy = "sourceLedger",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<SourceLedgerAssignment> sourceLedgerAssignments;

    @OneToMany(
        mappedBy = "sourceLedger",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<SourceLedgerSignature> sourceLedgerSignatures;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

}
