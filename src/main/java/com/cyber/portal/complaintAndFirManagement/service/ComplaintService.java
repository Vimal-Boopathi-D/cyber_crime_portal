package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.citizenManagement.dto.ComplaintHistoryDto;
import com.cyber.portal.complaintAndFirManagement.dto.*;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    String submitComplaint(ComplaintRequestDTO complaint);
    ComplaintDto getComplaintByAckNo(String acknowledgementNo);
    List<ComplaintHistoryDto> getCitizenComplaints(Long citizenId);
    Complaint updateStatus(Long complaintId, IncidentStatus status, String remarks, String officer);
    List<ComplaintTimelineResponseDTO> getTimeline(Long complaintId);
    Optional<Complaint> trackById(Long id);
    List<ComplaintDto> getComplaints(Long citizenId);
    AssignedOfficerDTO getAssignedOfficer(Long complaintId);
    List<ComplaintMonthlyCountDto> getMonthlyComplaintStats();
    Resource downloadFir(Long id);
}
