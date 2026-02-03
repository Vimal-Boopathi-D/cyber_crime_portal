package com.cyber.portal.complaintAndFirManagement.dto;

import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintTimelineResponseDTO {
    private Long id;
    private IncidentStatus status;
    private String remarks;
    private String updatedBy;
    private LocalDateTime updatedAt;
}

