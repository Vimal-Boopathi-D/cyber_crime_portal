package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.complaintAndFirManagement.service.DocumentService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.ContentType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(
            value = "/complaints/{id}/{officerId}/upload-fir",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )    public ResponseEntity<ApiResponse<FIR>> uploadFIR(
            @PathVariable("id") Long id,
            @PathVariable("officerId") Long officerId,
            @RequestParam("firDocument") MultipartFile firDocument) {
       documentService.uploadFIR(id, officerId, firDocument);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "FIR uploaded successfully",null));
    }

    @GetMapping("/complaints/{id}/download-fir")
    public ResponseEntity<Resource> downloadFIR(@PathVariable Long id, HttpServletRequest request) {

        Resource resource =complaintService.downloadFir(id);
        String contentType;
        try {
            contentType=request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            contentType=null;
        }
        if (contentType==null){
            contentType= MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName= \"" + resource.getFilename()+ "\"")
                .body(resource);
    }

    @Data
    public static class StatusUpdateRequest {
        private IncidentStatus status;
        private String remarks;
        private String officerName;
    }
}
