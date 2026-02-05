package com.cyber.portal.volunteerManagement.service.impl;

import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.volunteerManagement.entity.Volunteer;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import com.cyber.portal.volunteerManagement.service.VolunteerCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerCsvServiceImpl implements VolunteerCsvService {

    private final VolunteerRepository volunteerRepository;

    public String generateApprovedVolunteersCsv() {

        List<Volunteer> list = volunteerRepository.findByStatus(VolunteerStatus.APPROVED);

        StringBuilder csv = new StringBuilder();

        // Header row
        csv.append("ID,Name,Email,Mobile,Gender,State,District,Type,Status\n");

        for (Volunteer v : list) {
            csv.append(v.getId()).append(",");
            csv.append(v.getVolunteerName()).append(",");
            csv.append(v.getEmail()).append(",");
            csv.append(v.getMobileNo()).append(",");
            csv.append(v.getGender()).append(",");
            csv.append(v.getState()).append(",");
            csv.append(v.getDistrict()).append(",");
            csv.append(v.getVolunteerType()).append(",");
            csv.append(v.getStatus()).append("\n");
        }

        return csv.toString();
    }
}
