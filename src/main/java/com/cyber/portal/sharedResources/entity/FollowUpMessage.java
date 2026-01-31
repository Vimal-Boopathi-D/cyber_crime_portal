package com.cyber.portal.sharedResources.entity;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follow_up_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUpMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(nullable = false)
    private String senderRole; // CITIZEN, POLICE_OFFICER, ADMIN

    @Column(nullable = false)
    private Long senderId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
}
