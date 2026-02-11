package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.citizenManagement.dto.ComplaintHistoryDto;
import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.repository.CitizenRepository;
import com.cyber.portal.citizenManagement.repository.PoliceOfficerRepository;
import com.cyber.portal.complaintAndFirManagement.dto.*;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import com.cyber.portal.complaintAndFirManagement.repository.FIRRepository;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.sharedResources.config.FakeKeywordConfig;
import com.cyber.portal.sharedResources.config.KeywordWeightConfig;
import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.exception.PortalException;
import com.cyber.portal.sharedResources.service.NotificationService;
import com.cyber.portal.sharedResources.util.TextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintTimelineRepository timelineRepository;
    private final NotificationService notificationService;
    private final CitizenRepository citizenRepository;
    private final FIRRepository firRepository;
    private final FakeKeywordConfig fakeKeywordConfig;
    private final KeywordWeightConfig keywordWeightConfig;

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
        complaint.setSuspectName(complaintRequestDTO.getSuspectName());
        complaint.setSuspectContact(complaintRequestDTO.getSuspectContact());
        complaint.setSuspectIdentificationDetails(complaintRequestDTO.getSuspectIdentificationDetails());
        complaint.setSuspectAdditionalInfo(complaintRequestDTO.getSuspectAdditionalInfo());
        analyze(complaintRequestDTO.getAdditionalInfo(), complaintRequestDTO.getCategory(), complaint);
        Complaint saved = complaintRepository.save(complaint);
        saveTimeline(saved, IncidentStatus.SUBMITTED, "Initial Submission", "Citizen");
        notificationService.sendStatusUpdate(saved.getId());
        return ackNo;
    }

    public void analyze(String text, ComplaintCategory category, Complaint complaint) {

        List<String> tokens = TextUtil.tokenize(text);
//        if (tokens.isEmpty()) {
//            complaint.setPercentage(0);
//            complaint.setLabel("GENUINE");
//            return;
//        }
        Set<String> suspiciousKeywords = fakeKeywordConfig.getKeywords(category);

        double fakeScore = 0;
        int totalWords = tokens.size();

        for (String word : suspiciousKeywords) {
            long count = tokens.stream()
                    .filter(token -> token.equals(word))
                    .count();

            if (count > 0) {
                double tf = (double) count / totalWords;
                double weight = keywordWeightConfig.getWeight(category, word);
                fakeScore += tf * weight;
            }
        }

        // 🔹 Normalize score → percentage
        double percentage = Math.min(fakeScore * 100, 100);

//        complaint.setPercentage(percentage);
//        complaint.setLabel(percentage >= 60 ? "POSSIBLY FAKE" : "LIKELY GENUINE");
    }

    @Override
    public ComplaintDto getComplaintByAckNo(String ack) {
        
        Complaint complaint = complaintRepository
                .findByAcknowledgementNo(ack)
                .orElseThrow(() -> new PortalException("Not Found", HttpStatus.NOT_FOUND));

        FIR fir = complaint.getFir();
        PoliceOfficer policeOfficer = Optional.ofNullable(complaint.getFir())
                .map(FIR::getGeneratedBy)
                .orElse(null);

        return ComplaintDto.builder()
                .id(complaint.getId())
                .acknowledgementNo(complaint.getAcknowledgementNo())
                .category(complaint.getCategory())
                .incidentDate(complaint.getIncidentDate())
                .additionalInfo(complaint.getAdditionalInfo())
                .incidentLocation(complaint.getIncidentLocation())
                .state(complaint.getState())
                .district(complaint.getDistrict())
                .policeStation(complaint.getPoliceStation())
                .status(complaint.getStatus())
                .citizenId(complaint.getCitizen() != null ? complaint.getCitizen().getId() : null)
                .citizenName(complaint.getCitizen() != null ? complaint.getCitizen().getName() : null)
                .citizenMobile(complaint.getCitizen() != null ? complaint.getCitizen().getMobileNo() : null)
                .firId(fir != null ? fir.getId() : null)
                .firNumber(fir != null ? fir.getFirNo() : null)
                .officerName(policeOfficer != null ? policeOfficer.getName() : null)
                .officerState(
                        policeOfficer != null && policeOfficer.getState() != null
                                ? policeOfficer.getState().toString()
                                : null
                )
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .suspectName(complaint.getSuspectName())
                .suspectContact(complaint.getSuspectContact())
                .suspectIdentificationDetails(complaint.getSuspectIdentificationDetails())
                .suspectAdditionalInfo(complaint.getSuspectAdditionalInfo())
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
        if (complaint.getStatus()==status) {
            return complaint;
        }
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
    public List<ComplaintDto> getComplaints(Long citizenId) {
        if (citizenId == null) {
            List<Complaint>complaints= complaintRepository.findAll();
            return complaints.stream()
                    .map(c->ComplaintDto.builder()
                            .id(c.getId())
                            .acknowledgementNo(c.getAcknowledgementNo())
                            .category(c.getCategory())

                            .incidentDate(c.getIncidentDate())
                            .additionalInfo(c.getAdditionalInfo())

                            .incidentLocation(c.getIncidentLocation())
                            .state(c.getState())
                            .district(c.getDistrict())
                            .policeStation(c.getPoliceStation())

                            .status(c.getStatus())
                            .citizenId(c.getCitizen() != null ? c.getCitizen().getId() : null)
                            .citizenName(c.getCitizen() != null ? c.getCitizen().getName() : null)
                            .citizenMobile(c.getCitizen() != null ? c.getCitizen().getMobileNo() : null)
                            .firId(c.getFir() != null ? c.getFir().getId() : null)
                            .firNumber(c.getFir() != null ? c.getFir().getFirNo() : null)
                            .createdAt(c.getCreatedAt())
                            .updatedAt(c.getUpdatedAt())
                            .suspectName(c.getSuspectName())
                            .suspectContact(c.getSuspectContact())
                            .suspectIdentificationDetails(c.getSuspectIdentificationDetails())
                            .suspectAdditionalInfo(c.getSuspectAdditionalInfo())
                            .build())
                    .toList();
        }else {
            List<Complaint>complaints= complaintRepository.findByCitizenId(citizenId);
            return complaints.stream()
                    .map(c->ComplaintDto.builder()
                            .id(c.getId())
                            .acknowledgementNo(c.getAcknowledgementNo())
                            .category(c.getCategory())

                            .incidentDate(c.getIncidentDate())
                            .additionalInfo(c.getAdditionalInfo())

                            .incidentLocation(c.getIncidentLocation())
                            .state(c.getState())
                            .district(c.getDistrict())
                            .policeStation(c.getPoliceStation())

                            .status(c.getStatus())
                            .citizenId(c.getCitizen() != null ? c.getCitizen().getId() : null)
                            .citizenName(c.getCitizen() != null ? c.getCitizen().getName() : null)
                            .citizenMobile(c.getCitizen() != null ? c.getCitizen().getMobileNo() : null)
                            .firId(c.getFir() != null ? c.getFir().getId() : null)
                            .firNumber(c.getFir() != null ? c.getFir().getFirNo() : null)
                            .suspectName(c.getSuspectName())
                            .suspectContact(c.getSuspectContact())
                            .suspectIdentificationDetails(c.getSuspectIdentificationDetails())
                            .suspectAdditionalInfo(c.getSuspectAdditionalInfo())
                            .createdAt(c.getCreatedAt())
                            .updatedAt(c.getUpdatedAt())
                            .build())
                    .toList();
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
        AssignedOfficerDTO dto = new AssignedOfficerDTO();
        dto.setId(officer.getId());
        dto.setRank(officer.getRank());
        dto.setOfficerCode(officer.getOfficerCode());
        dto.setName(officer.getName());
        dto.setState(officer.getState().toString());

        return dto;
    }

    public List<ComplaintMonthlyCountDto> getMonthlyComplaintStats() {
        int year = LocalDateTime.now().getYear();
        List<Object[]> rows = complaintRepository.getComplaintCountMonthWise(year);

        return rows.stream()
                .map(r -> new ComplaintMonthlyCountDto(
                        ((Number) r[0]).intValue(),
                        ((Number) r[1]).intValue(),
                        ((Number) r[2]).longValue()
                ))
                .toList();
    }

}
