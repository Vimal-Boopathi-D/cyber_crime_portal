package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GACAppealRepository extends JpaRepository<GACAppeal, Long> {

    Optional<GACAppeal> findByComplaint_Id(Long complaintId);
}
