package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByAcknowledgementNo(String acknowledgementNo);
    List<Complaint> findByCitizenId(Long citizenId);

    @org.springframework.data.jpa.repository.Query("SELECT c.category, COUNT(c) FROM Complaint c GROUP BY c.category")
    List<Object[]> countComplaintsByCategory();

    @org.springframework.data.jpa.repository.Query("SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status")
    List<Object[]> countComplaintsByStatus();

    @org.springframework.data.jpa.repository.Query("SELECT c.state, COUNT(c) FROM Complaint c GROUP BY c.state")
    List<Object[]> countComplaintsByState();
}
