package com.cyber.portal.volunteerManagement.service;

import com.cyber.portal.volunteerManagement.dto.VolunteerRegistrationDto;
import org.springframework.web.multipart.MultipartFile;
import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.volunteerManagement.entity.Volunteer;

import java.util.List;

public interface VolunteerService {
    void register(VolunteerRegistrationDto dto, MultipartFile resume, MultipartFile photo);
    List<Volunteer> getVolunteerByStatus(VolunteerStatus status);
    void updateStatus(Long id, VolunteerStatus status);
    List<Volunteer> getAllVolunteersList();
}
