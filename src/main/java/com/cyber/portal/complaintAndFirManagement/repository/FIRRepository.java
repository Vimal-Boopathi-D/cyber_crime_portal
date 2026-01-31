package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FIRRepository extends JpaRepository<FIR, Long> {
    Optional<FIR> findByComplaintId(Long complaintId);
}
