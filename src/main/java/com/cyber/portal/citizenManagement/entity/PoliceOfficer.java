package com.cyber.portal.citizenManagement.entity;

import com.cyber.portal.sharedResources.enums.State;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officer_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String officerCode;

    private String name;

    private String rank;

    @Enumerated(EnumType.STRING)
    private State state;
}

