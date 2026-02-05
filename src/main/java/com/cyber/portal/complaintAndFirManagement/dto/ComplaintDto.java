package com.cyber.portal.complaintAndFirManagement.dto;

import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.enums.State;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ComplaintDto {

    private Long id;
    private String acknowledgementNo;

    private ComplaintCategory category;

    private LocalDateTime incidentDate;
    private String reasonForDelay;
    private String additionalInfo;

    private String incidentLocation;
    private State state;
    private String district;
    private String policeStation;

    private IncidentStatus status;

    private String suspectName;
    private String suspectContact;
    private String suspectIdentificationDetails;
    private String suspectAdditionalInfo;
    private Long citizenId;
    private String citizenName;
    private String citizenMobile;
    private Long firId;
    private String firNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
