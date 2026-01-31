package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.service.DocumentService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/complaint-report/{complaintId}")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long complaintId) {
        byte[] content = documentService.generateComplaintReport(complaintId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=complaint-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/fir/{complaintId}")
    public ResponseEntity<byte[]> downloadFIR(@PathVariable Long complaintId) {
        byte[] content = documentService.getFIRCopy(complaintId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fir.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }
}
