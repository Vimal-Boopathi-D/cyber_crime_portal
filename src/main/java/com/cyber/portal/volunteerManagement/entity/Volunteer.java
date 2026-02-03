package com.cyber.portal.volunteerManagement.entity;

import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.sharedResources.enums.VolunteerType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private String title;

    @Column(nullable = false)
    private String volunteerName;

    private String fatherOrMotherName;

    @Column(nullable = false, length = 15)
    private String mobileNo;

    @Enumerated(EnumType.STRING)
    private VolunteerStatus status;

    @Enumerated(EnumType.STRING)
    private VolunteerType type;
    @Column(nullable = false)
    private String email;

    private String gender;
    private LocalDate dateOfBirth;
    private String occupation;
    private String qualification;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    @Enumerated(EnumType.STRING)
    private VolunteerType volunteerType;

    private String resumePath;
    private String homePhone;
    private String nationalIdType;
    private String nationalIdNumber;
    private String passportPhotoPath;

    private String houseNo;
    private String streetName;
    private String country;
    private String state;
    private String district;
    private String cityOrVillage;
    private String pincode;

    @Column(nullable = false)
    private Boolean approved;

    @PrePersist
    public void setDefaultValues() {
        if (approved == null) {
            approved = false;
        }
    }

}
