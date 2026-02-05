package com.cyber.portal.volunteerManagement.repository;

import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.volunteerManagement.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    List<Volunteer> findByStatus(VolunteerStatus status);

}
