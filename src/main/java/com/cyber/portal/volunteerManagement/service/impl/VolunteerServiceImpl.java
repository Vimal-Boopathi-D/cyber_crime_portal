package com.cyber.portal.volunteerManagement.service.impl;

import com.cyber.portal.volunteerManagement.dto.VolunteerRegistrationDto;
import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.volunteerManagement.entity.Volunteer;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import com.cyber.portal.volunteerManagement.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private static final String UPLOAD_DIR = "uploads/volunteers/";

    @Override
    public void register(VolunteerRegistrationDto dto,
                         MultipartFile resume,
                         MultipartFile photo) {

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
            String photoFileName  = UUID.randomUUID() + "_" + photo.getOriginalFilename();

            Path resumePath = Paths.get(UPLOAD_DIR, resumeFileName);
            Path photoPath  = Paths.get(UPLOAD_DIR, photoFileName);

            Files.copy(resume.getInputStream(), resumePath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(photo.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Resume saved to: " + resumePath.toString()+"volunteer name :"+dto.getVolunteerName()+" file name :"+resumeFileName);
            Volunteer volunteer = Volunteer.builder()
                    .title(dto.getTitle())
                    .volunteerName(dto.getVolunteerName())
                    .fatherOrMotherName(dto.getFatherOrMotherName())
                    .mobileNo(dto.getMobileNo())
                    .email(dto.getEmail())
                    .gender(dto.getGender())
                    .dateOfBirth(dto.getDateOfBirth())
                    .occupation(dto.getOccupation())
                    .qualification(dto.getQualification())
                    .certifications(dto.getCertifications())
                    .volunteerType(dto.getVolunteerType())
                    .homePhone(dto.getHomePhone())
                    .nationalIdType(dto.getNationalIdType())
                    .nationalIdNumber(dto.getNationalIdNumber())

                    .resumePath(resumePath.toString())
                    .passportPhotoPath(photoPath.toString())

                    .houseNo(dto.getHouseNo())
                    .streetName(dto.getStreetName())
                    .country(dto.getCountry())
                    .state(dto.getState())
                    .district(dto.getDistrict())
                    .cityOrVillage(dto.getCityOrVillage())
                    .pincode(dto.getPincode())
                    .status(VolunteerStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();


            volunteerRepository.save(volunteer);

        } catch (IOException e) {
            throw new RuntimeException("Failed to register volunteer", e);
        }
    }

    public List<Volunteer> getVolunteerByStatus(VolunteerStatus status) {
        return volunteerRepository.findByStatus(status);
    }

    @Override
    public void updateStatus(Long id, VolunteerStatus status) {
         Volunteer volunteer= volunteerRepository.findById(id).orElseThrow(()->new RuntimeException("No Volunteer Found"));
         volunteer.setStatus(status);
         volunteerRepository.save(volunteer);
    }

    @Override
    public List<Volunteer> getAllVolunteersList() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers;
    }
}
