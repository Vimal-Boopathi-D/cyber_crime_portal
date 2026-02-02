package com.cyber.portal.citizenManagement.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "police_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    private String city;

    private String area;

    private String pincode;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

