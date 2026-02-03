package com.cyber.portal.suspectManagement.entity;

import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "suspect_registry",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"identifier_type", "identifier_value"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuspectRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mobile / Email / Bank / URL / etc.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuspectIdentifierType identifierType;

    @Column(name = "identifier_value", nullable = false)
    private String identifierValue;

    // From UI
    @Enumerated(EnumType.STRING)
    private State incidentState;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Evidence (file upload path)
    private String evidencePath;

    // How many times reported
    private int reportCount;

    private LocalDateTime firstReportedAt;
    private LocalDateTime lastReportedAt;

    @PrePersist
    protected void onCreate() {
        firstReportedAt = LocalDateTime.now();
        lastReportedAt = LocalDateTime.now();
        reportCount = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        lastReportedAt = LocalDateTime.now();
    }
}
