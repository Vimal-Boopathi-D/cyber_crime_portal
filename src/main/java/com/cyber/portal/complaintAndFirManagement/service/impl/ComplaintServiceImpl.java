package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.exception.PortalException;
import com.cyber.portal.sharedResources.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintTimelineRepository timelineRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Complaint submitComplaint(Complaint complaint) {
        complaint.setAcknowledgementNo("ACK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        Complaint saved = complaintRepository.save(complaint);
        saveTimeline(saved, IncidentStatus.SUBMITTED, "Initial Submission", "Citizen");
        notificationService.sendStatusUpdate(saved.getId());
        return saved;
    }

    @Override
    public Optional<Complaint> getComplaintByAckNo(String ack) {
        return complaintRepository.findByAcknowledgementNo(ack);
    }

    @Override
    public List<Complaint> getCitizenComplaints(Long id) {
        return complaintRepository.findByCitizenId(id);
    }

    @Override
    @Transactional
    public Complaint updateStatus(Long id, IncidentStatus status, String remarks, String user) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new PortalException("Not Found", HttpStatus.NOT_FOUND));
        complaint.setStatus(status);
        Complaint updated = complaintRepository.save(complaint);
        saveTimeline(updated, status, remarks, user);
        notificationService.sendStatusUpdate(updated.getId());
        return updated;
    }

    @Override
    public List<ComplaintTimeline> getTimeline(Long id) {
        return timelineRepository.findByComplaintIdOrderByUpdatedAtDesc(id);
    }

    @Override
    public Optional<Complaint> trackById(Long id) {
        return complaintRepository.findById(id);
    }

    private void saveTimeline(Complaint c, IncidentStatus s, String r, String u) {
        timelineRepository.save(ComplaintTimeline.builder()
                .complaint(c).status(s).remarks(r).updatedBy(u).build());
    }
}
