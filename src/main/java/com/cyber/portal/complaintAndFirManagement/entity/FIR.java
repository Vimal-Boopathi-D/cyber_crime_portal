package com.cyber.portal.complaintAndFirManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "firs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FIR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String firNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    private LocalDateTime generatedAt;
    private String generatedBy;
    private String filePath;

    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}
