package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.FIR;

public interface DocumentService {
    byte[] generateComplaintReport(Long complaintId);
    byte[] getFIRCopy(Long complaintId);
    FIR uploadFIR(Long complaintId, String firNo, String officerName);
}
