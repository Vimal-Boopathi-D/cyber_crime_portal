package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FIRRepository extends JpaRepository<FIR, Long> {
    Optional<FIR> findByComplaintId(Long complaintId);

    @Query("""
        SELECT f
        FROM FIR f
        JOIN FETCH f.complaint c
        JOIN FETCH f.generatedBy p
        WHERE f.id = :firId
    """)
    Optional<FIR> getFir(@Param("firId") Long firId);
}

