package com.cyber.portal.citizenManagement.dto;

import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComplaintHistoryDto {
    private Long id;
    private String acknowledgementNo;
    private ComplaintCategory category;
    private LocalDateTime incidentDate;
    private String incidentLocation;
    private State state;
    private String district;
    private String policeStation;
    private IncidentStatus status;
}
