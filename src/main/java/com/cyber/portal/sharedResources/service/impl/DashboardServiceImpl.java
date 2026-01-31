package com.cyber.portal.sharedResources.service.impl;

import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.sharedResources.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final ComplaintRepository complaintRepository;

    @Override
    public Map<String, Long> getComplaintsByCategory() {
        return convertToMap(complaintRepository.countComplaintsByCategory());
    }

    @Override
    public Map<String, Long> getComplaintsByStatus() {
        return convertToMap(complaintRepository.countComplaintsByStatus());
    }

    @Override
    public Map<String, Long> getComplaintsByState() {
        return convertToMap(complaintRepository.countComplaintsByState());
    }

    @Override
    public Map<String, Object> getOverallStats() {
        Map<String, Object> stats = new HashMap<>();
        long total = complaintRepository.count();
        Map<String, Long> statusStats = getComplaintsByStatus();
        long resolved = statusStats.getOrDefault("CLOSED", 0L) + statusStats.getOrDefault("RESOLVED", 0L);
        
        stats.put("totalComplaints", total);
        stats.put("pendingComplaints", total - resolved);
        stats.put("resolvedComplaints", resolved);
        stats.put("byCategory", getComplaintsByCategory());
        stats.put("byStatus", statusStats);
        stats.put("byRegion", getComplaintsByState());
        return stats;
    }

    private Map<String, Long> convertToMap(List<Object[]> results) {
        return results.stream()
                .collect(Collectors.toMap(
                        result -> String.valueOf(result[0]),
                        result -> (Long) result[1]
                ));
    }
}
