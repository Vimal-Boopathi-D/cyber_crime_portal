package com.cyber.portal.sharedResources.service.impl;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import com.cyber.portal.complaintAndFirManagement.repository.FIRRepository;
import com.cyber.portal.complaintAndFirManagement.repository.GACAppealRepository;
import com.cyber.portal.sharedResources.dto.citizenSummaryDTO;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.service.DashboardService;
import com.cyber.portal.suspectManagement.repository.SuspectRepository;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final ComplaintRepository complaintRepository;
    private final FIRRepository firRepository;
    private final GACAppealRepository gacAppealRepository;
    private final ComplaintTimelineRepository timelineRepository;
    private final SuspectRepository suspectRepository;
    private final VolunteerRepository volunteerRepository;

    @Override
    public Map<String, Long> getComplaintsByCategory(Long periodDays) {
        if (periodDays == null) {
            return convertToMap(complaintRepository.countComplaintsByCategory());
        }else {
        return convertToMap(complaintRepository.countComplaintsByCategoryFromDate(LocalDateTime.now().minusDays(periodDays)));
        }
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
        long SuspectCount=suspectRepository.count();
        long volunteerCount=volunteerRepository.count();
        Map<String, Long> statusStats = getComplaintsByStatus();
        long resolved = statusStats.getOrDefault("CLOSED", 0L) + statusStats.getOrDefault("RESOLVED", 0L);
        Double avgProcessingTime = complaintRepository
                .findAverageProcessingTime();

        LocalDate today = LocalDate.now();

        LocalDateTime startOfThisWeek =
                today.with(DayOfWeek.MONDAY).atStartOfDay();

        LocalDateTime startOfLastWeek =
                startOfThisWeek.minusWeeks(1);

        LocalDateTime endOfLastWeek =
                startOfThisWeek.minusSeconds(1);

        long lastWeekTotal =
                complaintRepository.countByCreatedAtBetween(
                        startOfLastWeek, endOfLastWeek);

        long lastWeekClosed =
                complaintRepository.countByStatusInAndCreatedAtBetween(
                        List.of(IncidentStatus.CLOSED,IncidentStatus.RECEIVED),
                        startOfLastWeek,
                        endOfLastWeek);

        long lastWeekPending =
                complaintRepository.countByStatusNotInAndCreatedAtBetween(
                        List.of(IncidentStatus.CLOSED,IncidentStatus.RECEIVED),
                        startOfLastWeek,
                        endOfLastWeek);

        long thisWeekTotal =
                complaintRepository.countByCreatedAtBetween(
                        startOfThisWeek, LocalDateTime.now());

        long thisWeekClosed =
                complaintRepository.countByStatusInAndCreatedAtBetween(
                        List.of(IncidentStatus.CLOSED,IncidentStatus.RESOLVED),
                        startOfThisWeek,
                        LocalDateTime.now());

        long thisWeekPending =
                complaintRepository.countByStatusNotInAndCreatedAtBetween(
                        List.of(IncidentStatus.CLOSED,IncidentStatus.RESOLVED),
                        startOfThisWeek,
                        LocalDateTime.now());

        double totalCountPercentage =
                (thisWeekTotal == 0 && lastWeekTotal == 0 )? 0 :
                        ((thisWeekTotal -lastWeekTotal) * 100.0) / (thisWeekTotal + lastWeekTotal);

        double totalWeekClosedPercentage =
                (thisWeekClosed == 0 && lastWeekClosed == 0) ? 0 :
                        ((thisWeekClosed -lastWeekClosed) * 100.0) / (thisWeekClosed + lastWeekClosed);

        double totalWeekPendingPercentage =
                (thisWeekPending == 0 && lastWeekPending == 0) ? 0 :
                        ((thisWeekPending -lastWeekPending) * 100.0) / (thisWeekPending + lastWeekPending);

        stats.put("totalComplaints", total);
        stats.put("pendingComplaints", total - resolved);
        stats.put("resolvedComplaints", resolved);
        stats.put("byCategory", getComplaintsByCategory(null));
        stats.put("byStatus", statusStats);
        stats.put("byRegion", getComplaintsByState());
        stats.put("averageProcessingTime",
                avgProcessingTime != null ? avgProcessingTime : 0);
        stats.put("Suspect Report",SuspectCount);
        stats.put("Volunteers",volunteerCount);
        stats.put("totalCountPercentage", totalCountPercentage);
        stats.put("totalWeekClosedPercentage", totalWeekClosedPercentage);
        stats.put("totalWeekPendingPercentage", totalWeekPendingPercentage);

        return stats;
    }

    private Map<String, Long> convertToMap(List<Object[]> results) {
        return results.stream()
                .collect(Collectors.toMap(
                        result -> String.valueOf(result[0]),
                        result -> (Long) result[1]
                ));
    }

    public Map<String, Object> getCitizenComplaints(Long citizenId) {

        List<Complaint> complaints =
                complaintRepository.findByCitizen_IdOrderByCreatedAtDesc(citizenId);

        long totalComplaints = complaints.size();

        long closedCases = complaintRepository
                .countByCitizen_IdAndStatus(citizenId, IncidentStatus.CLOSED);

        long pendingCases = totalComplaints - closedCases;

        List<Map<String, Object>> complaintList = new ArrayList<>();

        for (Complaint complaint : complaints) {

            Map<String, Object> complaintMap = new HashMap<>();
            complaintMap.put("complaint", complaint);

            var firOpt = firRepository.findByComplaintId(complaint.getId());
            complaintMap.put("fir", firOpt.orElse(null));

            if (firOpt.isPresent()) {
                var officer = firOpt.get().getGeneratedBy();
                complaintMap.put("assignedOfficer", officer);
                complaintMap.put("officerName", officer.getName());
                complaintMap.put("policeStationState", officer.getState());
            }

            complaintMap.put("gacAppeal",
                    gacAppealRepository.findByComplaint_Id(complaint.getId()).orElse(null));

            complaintMap.put("timeline",
                    timelineRepository.findByComplaintIdOrderByUpdatedAtDesc(
                            complaint.getId()));

            complaintList.add(complaintMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalComplaints", totalComplaints);
        response.put("closedCases", closedCases);
        response.put("pendingCases", pendingCases);
        response.put("complaints", complaintList);

        return response;
    }

    public citizenSummaryDTO getDashboardSummary(Long citizenId) {

        long total = complaintRepository.countByCitizenId(citizenId);

        long closed = complaintRepository.countByCitizenIdAndStatus(
                citizenId, IncidentStatus.CLOSED
        );
        long resolved= complaintRepository.countByCitizenIdAndStatus(citizenId,IncidentStatus.RESOLVED);
        long pending = total - closed+resolved;
        citizenSummaryDTO dto = new citizenSummaryDTO();
        dto.setTotalComplaints(total);
        dto.setClosedCases(closed);
        dto.setPendingCases(pending);

        return dto;
    }

}
