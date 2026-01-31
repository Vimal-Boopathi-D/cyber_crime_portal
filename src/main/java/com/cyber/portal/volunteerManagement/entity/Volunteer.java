package com.cyber.portal.volunteerManagement.entity;

import com.cyber.portal.sharedResources.enums.VolunteerType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "volunteers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String mobileNo;

    @Enumerated(EnumType.STRING)
    private VolunteerType type;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private boolean isApproved;
}
