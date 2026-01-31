package com.cyber.portal.suspectManagement.entity;

import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suspect_registry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuspectRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SuspectIdentifierType identifierType;

    @Column(nullable = false)
    private String identifierValue;

    private int reportCount;
}
