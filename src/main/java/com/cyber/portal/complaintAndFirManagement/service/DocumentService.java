package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;

public interface DocumentService {
    byte[] generateComplaintReport(Long complaintId);
    byte[] getFIRCopy(Long complaintId);
    void uploadFIR(Long complaintId, String firNo, Long officerId);
    byte[] generateCitizenComplaintExcel(Long citizenId);
}
