package com.cyber.portal.sharedResources.service;

import java.util.Map;

public interface DashboardService {
    Map<String, Long> getComplaintsByCategory();
    Map<String, Long> getComplaintsByStatus();
    Map<String, Long> getComplaintsByState();
    Map<String, Object> getOverallStats();
}
