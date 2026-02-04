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

        byte[] pdfBytes = documentService.generateComplaintReport(complaintId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Complaint_Report_" + complaintId + ".pdf"
                )
                .contentLength(pdfBytes.length)
                .body(pdfBytes);
    }


    @GetMapping("/fir/{firId}")
    public ResponseEntity<byte[]> downloadFIR(@PathVariable Long firId) {

        byte[] pdfBytes = documentService.getFIRCopy(firId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=FIR_" + firId + ".pdf"
                )
                .contentLength(pdfBytes.length)
                .body(pdfBytes);
    }


    @GetMapping("/citizen/complaints/excel")
    public ResponseEntity<byte[]> downloadCitizenComplaintsExcel(
            @RequestParam(required = false) Long citizenId) {

        byte[] excelData =
                documentService.generateCitizenComplaintExcel(citizenId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=citizen-complaints.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelData.length)
                .body(excelData);
    }

}
