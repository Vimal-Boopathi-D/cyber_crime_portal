package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.complaintAndFirManagement.dto.GACAppealRequestDTO;
import com.cyber.portal.complaintAndFirManagement.dto.GACAppealResponseDTO;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.GACAppealRepository;
import com.cyber.portal.complaintAndFirManagement.service.GACAppealService;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.exception.PortalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GACAppealServiceImpl implements GACAppealService {
    private final GACAppealRepository gacAppealRepository;
    private final ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public void submitAppeal(GACAppealRequestDTO dto) {

        Complaint complaint = complaintRepository.findById(dto.getComplaintId())
                .orElseThrow(() ->
                        new PortalException("Complaint not found", HttpStatus.NOT_FOUND));

        GACAppeal appeal = new GACAppeal();
        appeal.setComplaint(complaint);
        appeal.setAppealReason(dto.getAppealReason());
        appeal.setStatus(IncidentStatus.UNDER_VERIFICATION);
        gacAppealRepository.save(appeal);
    }


    @Override
    @Transactional
    public GACAppealResponseDTO getAppeal(Long id) {

        return gacAppealRepository.findById(id)
                .map(appeal -> GACAppealResponseDTO.builder()
                        .id(appeal.getId())
                        .complaintId(appeal.getComplaint().getId())
                        .appealReason(appeal.getAppealReason())
                        .status(appeal.getStatus())
                        .appealDate(appeal.getAppealDate())
                        .build()
                )
                .orElseThrow(() ->
                        new PortalException("GAC Appeal not found", HttpStatus.NOT_FOUND));
    }

}
