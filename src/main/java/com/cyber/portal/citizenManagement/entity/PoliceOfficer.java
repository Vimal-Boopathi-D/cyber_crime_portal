package com.cyber.portal.citizenManagement.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "police_officer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliceOfficer {

    @Id
    @Column(name = "officer_id")
    private Long officerId;

    /**
     * One-to-One mapping with User table
     * officer_id is both PK and FK
     */
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "officer_id")
    private Citizen citizen;

    @Column(name = "badge_number", unique = true, nullable = false)
    private String badgeNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id")
    private PoliceStation policeStation;

    private String rank;

    @Column(name = "active_cases")
    private Integer activeCases;

    @Column(name = "is_available")
    private Boolean isAvailable;
}

