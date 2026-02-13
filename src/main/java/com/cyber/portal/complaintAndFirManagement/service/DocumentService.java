package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    byte[] generateComplaintReport(Long complaintId);
    byte[] getFIRCopy(Long firId);
    void uploadFIR(Long complaintId, Long officerId, MultipartFile firDocument);
    byte[] generateCitizenComplaintExcel(Long citizenId);
}
