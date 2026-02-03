package com.cyber.portal.volunteerManagement.service;

import com.cyber.portal.volunteerManagement.dto.VolunteerRegistrationDto;
import org.springframework.web.multipart.MultipartFile;

public interface VolunteerService {
    void register(VolunteerRegistrationDto dto, MultipartFile resume, MultipartFile photo);
}
