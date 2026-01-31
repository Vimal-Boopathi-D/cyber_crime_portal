package com.cyber.portal.complaintAndFirManagement.entity;

import com.cyber.portal.sharedResources.enums.IncidentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gac_appeals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GACAppeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(columnDefinition = "TEXT")
    private String appealReason;

    private LocalDateTime appealDate;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    @PrePersist
    protected void onCreate() {
        appealDate = LocalDateTime.now();
    }
}
