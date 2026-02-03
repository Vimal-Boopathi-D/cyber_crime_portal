package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.citizenManagement.dto.ComplaintHistoryDto;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintDto;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintRequestDTO;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintTimelineResponseDTO;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    String submitComplaint(ComplaintRequestDTO complaint);
    ComplaintDto getComplaintByAckNo(String acknowledgementNo);
    List<ComplaintHistoryDto> getCitizenComplaints(Long citizenId);
    Complaint updateStatus(Long complaintId, IncidentStatus status, String remarks, String officer);
    List<ComplaintTimelineResponseDTO> getTimeline(Long complaintId);
    Optional<Complaint> trackById(Long id);
    List<Complaint> getComplaints(Long citizenId);
}
