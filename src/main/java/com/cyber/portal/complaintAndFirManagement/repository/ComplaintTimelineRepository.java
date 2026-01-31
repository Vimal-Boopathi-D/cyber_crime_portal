package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintTimelineRepository extends JpaRepository<ComplaintTimeline, Long> {
    List<ComplaintTimeline> findByComplaintIdOrderByUpdatedAtDesc(Long complaintId);
}
