package com.toro.backend.infrastructure.database.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "vessels")
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @Column(name = "fishing_method")
    private String fishingMethod;

    @Column(name = "captain_name")
    private String captainName;

    @Column(name = "fishing_license")
    private String fishingLicense;

    @Column(name = "imo_number")
    private String imoNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    // Relationships
    @OneToOne(mappedBy = "vessel")
    private SourceLedger sourceLedger;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

}
