package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.service.AITrackingService;
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
public class ComplaintController {
    private final ComplaintService complaintService;
    private final AITrackingService aiTrackingService;
    private final ReportService reportService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<Complaint>> submit(@RequestBody Complaint complaint) {
        Complaint saved = complaintService.submitComplaint(complaint);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Complaint submitted successfully", saved));
    }

    @GetMapping("/track/{ackNo}")
    public ResponseEntity<ApiResponse<Complaint>> track(@PathVariable String ackNo) {
        return complaintService.getComplaintByAckNo(ackNo)
                .map(complaint -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Complaint found", complaint)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Complaint not found", null)));
    }

    @GetMapping("/{id}/timeline")
    public ResponseEntity<ApiResponse<List<ComplaintTimeline>>> getTimeline(@PathVariable Long id) {
        List<ComplaintTimeline> timeline = complaintService.getTimeline(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Timeline retrieved", timeline));
    }

    @GetMapping("/{id}/ai-tracking")
    public ResponseEntity<ApiResponse<Map<String, String>>> getAITracking(@PathVariable Long id) {
        return complaintService.trackById(id) // I need to add trackById to ComplaintService
                .map(complaint -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "AI prediction retrieved", aiTrackingService.getPredictiveUpdate(complaint.getStatus()))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.of(HttpStatus.NOT_FOUND, "Complaint not found", null)));
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {
        return complaintService.trackById(id)
                .map(complaint -> {
                    byte[] report = reportService.generateComplaintReport(complaint);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_" + complaint.getAcknowledgementNo() + ".txt")
                            .contentType(MediaType.TEXT_PLAIN) // Changed to plain text for now
                            .body(report);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
