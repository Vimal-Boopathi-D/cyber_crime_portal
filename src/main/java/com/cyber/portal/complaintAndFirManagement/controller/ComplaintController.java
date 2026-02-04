package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.dto.*;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComplaintController {
    private final ComplaintService complaintService;
    private final ReportService reportService;


    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<String>> submit(@RequestBody ComplaintRequestDTO complaint) {
        String saved = complaintService.submitComplaint(complaint);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Complaint submitted successfully", saved));
    }

    @GetMapping("/track/{ackNo}")
    public ResponseEntity<ApiResponse<ComplaintDto>> track(@PathVariable String ackNo) {
        ComplaintDto complaint= complaintService.getComplaintByAckNo(ackNo);
        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "Complaint found", complaint)
        );
    }

    @GetMapping("/{id}/timeline")
    public ResponseEntity<ApiResponse<List<ComplaintTimelineResponseDTO>>> getTimeline(@PathVariable Long id) {
        List<ComplaintTimelineResponseDTO> timeline = complaintService.getTimeline(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Timeline retrieved", timeline));
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {
        return complaintService.trackById(id)
                .map(complaint -> {
                    byte[] report = reportService.generateComplaintReport(complaint);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_" + complaint.getAcknowledgementNo() + ".txt")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(report);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ComplaintDto>>> getComplaints(
            @RequestParam(required = false) Long citizenId) {
        List<ComplaintDto> complaints = complaintService.getComplaints(citizenId);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Fetched all complaints", complaints));
    }

    @GetMapping("/{complaintId}/assigned-officer")
    public AssignedOfficerDTO getAssignedOfficer(
            @PathVariable Long complaintId) {
        return complaintService.getAssignedOfficer(complaintId);
    }

    @GetMapping("/monthly-count")
    public ResponseEntity<ApiResponse<List<ComplaintMonthlyCountDto>>> getMonthlyComplaintCount() {

        List<ComplaintMonthlyCountDto> complaintsCount =  complaintService.getMonthlyComplaintStats();

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Fetched all complaints count", complaintsCount));
    }
}
