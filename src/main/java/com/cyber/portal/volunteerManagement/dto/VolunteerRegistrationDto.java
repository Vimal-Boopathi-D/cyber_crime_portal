package com.cyber.portal.volunteerManagement.dto;

import com.cyber.portal.sharedResources.enums.VolunteerType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerRegistrationDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Volunteer name is required")
    private String volunteerName;

    @NotBlank(message = "Father/Mother name is required")
    private String fatherOrMotherName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNo;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    private String certifications;

    @NotNull(message = "Volunteer type is required")
    private VolunteerType volunteerType;

    private String homePhone;

    @NotBlank(message = "National ID type is required")
    private String nationalIdType;

    @NotBlank(message = "National ID number is required")
    private String nationalIdNumber;

    private String resumePath;
    private String passportPhotoPath;

    @NotBlank(message = "House number is required")
    private String houseNo;

    @NotBlank(message = "Street name is required")
    private String streetName;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "City/Village is required")
    private String cityOrVillage;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^\\d{6}$", message = "Invalid pincode")
    private String pincode;
}

