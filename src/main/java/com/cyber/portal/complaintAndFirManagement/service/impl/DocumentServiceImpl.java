package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.FIRRepository;
import com.cyber.portal.complaintAndFirManagement.service.DocumentService;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.exception.PortalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ComplaintRepository complaintRepository;
    private final FIRRepository firRepository;

    @Override
    public byte[] generateComplaintReport(Long complaintId) {
        // Mock PDF generation
        return "Complaint Report Content".getBytes();
    }

    @Override
    public byte[] getFIRCopy(Long complaintId) {
        // Mock FIR retrieval
        return "FIR Copy Content".getBytes();
    }

    @Override
    @Transactional
    public FIR uploadFIR(Long complaintId, String firNo, String officerName) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new PortalException("Complaint not found", HttpStatus.NOT_FOUND));
        
        FIR fir = FIR.builder()
                .firNo(firNo)
                .complaint(complaint)
                .generatedBy(officerName)
                .filePath("/uploads/fir/" + firNo + ".pdf")
                .build();
        
        complaint.setStatus(IncidentStatus.FIR_GENERATED);
        complaintRepository.save(complaint);
        
        return firRepository.save(fir);
    }
}
