package com.cyber.portal.complaintAndFirManagement.dto;

import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GACAppealResponseDTO {

    private Long id;
    private Long complaintId;
    private String appealReason;
    private IncidentStatus status;
    private LocalDateTime appealDate;
}

