package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.complaintAndFirManagement.service.DocumentService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/police")
@RequiredArgsConstructor
public class PoliceController {
    private final ComplaintService complaintService;
    private final DocumentService documentService;

    @PostMapping("/complaints/{id}/status")
    public ResponseEntity<ApiResponse<Complaint>> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        Complaint updated = complaintService.updateStatus(
                id, request.getStatus(), request.getRemarks(), request.getOfficerName());
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Status updated successfully", updated));
    }

    @PostMapping("/complaints/{id}/upload-fir")
    public ResponseEntity<ApiResponse<FIR>> uploadFIR(
            @PathVariable Long id,
            @RequestParam String firNo,
            @RequestParam String officerName) {
        var fir = documentService.uploadFIR(id, firNo, officerName);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "FIR uploaded successfully", fir));
    }

    @Data
    public static class StatusUpdateRequest {
        private IncidentStatus status;
        private String remarks;
        private String officerName;
    }
}
