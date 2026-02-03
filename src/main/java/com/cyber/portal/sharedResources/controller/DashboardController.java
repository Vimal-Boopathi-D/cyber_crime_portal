package com.cyber.portal.sharedResources.controller;

import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.dto.citizenSummaryDTO;
import com.cyber.portal.sharedResources.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOverallStats() {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Overall stats retrieved", dashboardService.getOverallStats()));
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getByCategory() {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Stats by category retrieved", dashboardService.getComplaintsByCategory()));
    }

    @GetMapping("/by-status")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getByStatus() {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Stats by status retrieved", dashboardService.getComplaintsByStatus()));
    }

    @GetMapping("/by-state")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getByState() {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Stats by state retrieved", dashboardService.getComplaintsByState()));
    }

    @GetMapping("/{citizenId}/complaints")
    public ResponseEntity<Map<String, Object>> getCitizenComplaints(
            @PathVariable Long citizenId) {

        return ResponseEntity.ok(
                dashboardService.getCitizenComplaints(citizenId)
        );
    }

    @GetMapping("/summary/{citizenId}")
    public citizenSummaryDTO getSummary(@PathVariable Long citizenId) {
        return dashboardService.getDashboardSummary(citizenId);
    }
}
