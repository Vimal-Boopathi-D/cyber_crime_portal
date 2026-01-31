package com.cyber.portal.volunteerManagement.repository;

import com.cyber.portal.volunteerManagement.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
