package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;

public interface DocumentService {
    byte[] generateComplaintReport(Long complaintId);
    byte[] getFIRCopy(Long firId);
    void uploadFIR(Long complaintId, Long officerId);
    byte[] generateCitizenComplaintExcel(Long citizenId);
}
