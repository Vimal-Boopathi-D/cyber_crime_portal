package com.cyber.portal.sharedResources.service;

import com.cyber.portal.sharedResources.dto.citizenSummaryDTO;

import java.util.Map;

public interface DashboardService {
    Map<String, Long> getComplaintsByCategory(Long periodDays);
    Map<String, Long> getComplaintsByStatus();
    Map<String, Long> getComplaintsByState();
    Map<String, Object> getOverallStats();
    Map<String, Object> getCitizenComplaints(Long citizenId);
    citizenSummaryDTO getDashboardSummary(Long citizenId);
}
