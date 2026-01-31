package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    Complaint submitComplaint(Complaint complaint);
    Optional<Complaint> getComplaintByAckNo(String acknowledgementNo);
    List<Complaint> getCitizenComplaints(Long citizenId);
    Complaint updateStatus(Long complaintId, IncidentStatus status, String remarks, String officer);
    List<ComplaintTimeline> getTimeline(Long complaintId);
    Optional<Complaint> trackById(Long id);
}
