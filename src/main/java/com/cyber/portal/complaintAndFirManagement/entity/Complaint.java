package com.cyber.portal.complaintAndFirManagement.entity;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String acknowledgementNo;

    private String category;
    private LocalDateTime incidentDate;
    
    @Column(columnDefinition = "TEXT")
    private String reasonForDelay;
    
    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @Enumerated(EnumType.STRING)
    private State state;
    private String district;
    private String policeStation;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = IncidentStatus.SUBMITTED;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
