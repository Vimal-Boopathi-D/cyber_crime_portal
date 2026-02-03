package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.citizenManagement.dto.ComplaintHistoryDto;
import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.entity.PoliceStation;
import com.cyber.portal.citizenManagement.repository.CitizenRepository;
import com.cyber.portal.complaintAndFirManagement.dto.AssignedOfficerDTO;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintDto;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintRequestDTO;
import com.cyber.portal.complaintAndFirManagement.dto.ComplaintTimelineResponseDTO;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import com.cyber.portal.complaintAndFirManagement.repository.FIRRepository;
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
    private final CitizenRepository citizenRepository;
    private final FIRRepository firRepository;

    @Override
    @Transactional
    public String submitComplaint(ComplaintRequestDTO complaintRequestDTO) {
        Complaint complaint = new Complaint();
        String ackNo = "ACK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        complaint.setAcknowledgementNo(ackNo);

        Citizen citizen = citizenRepository.findById(complaintRequestDTO.getCitizenId()).orElseThrow(() -> new RuntimeException("Citizen not found"));
        complaint.setCitizen(citizen);
        complaint.setCategory(complaintRequestDTO.getCategory());
        complaint.setIncidentDate(complaintRequestDTO.getIncidentDate());
        complaint.setIncidentLocation(complaintRequestDTO.getIncidentLocation());
        complaint.setReasonForDelay(complaintRequestDTO.getReasonForDelay());
        complaint.setState(complaintRequestDTO.getState());
        complaint.setDistrict(complaintRequestDTO.getDistrict());
        complaint.setPoliceStation(complaintRequestDTO.getPoliceStation());
        complaint.setStatus(IncidentStatus.SUBMITTED);
        complaint.setAdditionalInfo(complaintRequestDTO.getAdditionalInfo());
        Complaint saved = complaintRepository.save(complaint);
        saveTimeline(saved, IncidentStatus.SUBMITTED, "Initial Submission", "Citizen");
        notificationService.sendStatusUpdate(saved.getId());
        return ackNo;
    }

    @Override
    public ComplaintDto getComplaintByAckNo(String ack) {
        Complaint complaint= complaintRepository.findByAcknowledgementNo(ack).orElseThrow(() -> new PortalException("Not Found", HttpStatus.NOT_FOUND));
        return ComplaintDto.builder()
                .id(complaint.getId())
                .acknowledgementNo(complaint.getAcknowledgementNo())
                .category(complaint.getCategory())

                .incidentDate(complaint.getIncidentDate())
                .reasonForDelay(complaint.getReasonForDelay())
                .additionalInfo(complaint.getAdditionalInfo())

                .incidentLocation(complaint.getIncidentLocation())
                .state(complaint.getState())
                .district(complaint.getDistrict())
                .policeStation(complaint.getPoliceStation())

                .status(complaint.getStatus())
                .citizenId(complaint.getCitizen() != null ? complaint.getCitizen().getId() : null)
                .citizenName(complaint.getCitizen() != null ? complaint.getCitizen().getName() : null)
                .citizenMobile(complaint.getCitizen() != null ? complaint.getCitizen().getMobileNo() : null)
                .firId(complaint.getFir() != null ? complaint.getFir().getId() : null)
                .firNumber(complaint.getFir() != null ? complaint.getFir().getFirNo() : null)
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .build();
    }

    @Override
    public List<ComplaintHistoryDto> getCitizenComplaints(Long citizenId) {
        return complaintRepository.findByCitizenId(citizenId)
                .stream()
                .map(c -> ComplaintHistoryDto.builder()
                        .id(c.getId())
                        .acknowledgementNo(c.getAcknowledgementNo())
                        .category(c.getCategory())
                        .incidentDate(c.getIncidentDate())
                        .incidentLocation(c.getIncidentLocation())
                        .state(c.getState())
                        .district(c.getDistrict())
                        .policeStation(c.getPoliceStation())
                        .status(c.getStatus())
                        .build())
                .toList();
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
    public List<ComplaintTimelineResponseDTO> getTimeline(Long id) {
        List<ComplaintTimeline> timelines= timelineRepository.findByComplaintIdOrderByUpdatedAtDesc(id);
        return timelines.stream()
                .map(t -> ComplaintTimelineResponseDTO.builder()
                        .id(t.getId())
                        .status(t.getStatus())
                        .remarks(t.getRemarks())
                        .updatedBy(t.getUpdatedBy())
                        .updatedAt(t.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public Optional<Complaint> trackById(Long id) {
        return complaintRepository.findById(id);
    }

    @Override
    public List<Complaint> getComplaints(Long citizenId) {
        if (citizenId == null) {
            return complaintRepository.findAll();
        }else {
            return complaintRepository.findByCitizenId(citizenId);
        }
    }

    private void saveTimeline(Complaint c, IncidentStatus s, String r, String u) {
        timelineRepository.save(ComplaintTimeline.builder()
                .complaint(c).status(s).remarks(r).updatedBy(u).build());
    }

    public AssignedOfficerDTO getAssignedOfficer(Long complaintId) {
        FIR fir = firRepository.findByComplaintId(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Officers not found")
                );
        PoliceOfficer officer = fir.getGeneratedBy();
        if (officer == null) {
            throw new RuntimeException("Officers not found");
        }
        Citizen citizen = officer.getCitizen();
        PoliceStation station = officer.getPoliceStation();
        AssignedOfficerDTO dto = new AssignedOfficerDTO();
        dto.setOfficerId(officer.getOfficerId());
        dto.setOfficerName(citizen.getName());
        dto.setRank(officer.getRank());
        dto.setBadgeNumber(officer.getBadgeNumber());
        dto.setMobileNo(citizen.getMobileNo());
        dto.setEmail(citizen.getEmail());

        return dto;
    }

}
