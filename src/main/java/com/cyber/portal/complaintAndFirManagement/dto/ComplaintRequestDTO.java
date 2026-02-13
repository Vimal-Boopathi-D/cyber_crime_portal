package com.cyber.portal.complaintAndFirManagement.dto;


import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import com.cyber.portal.sharedResources.enums.State;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintRequestDTO {
    @NotNull(message = "Complaint category is required")
    private ComplaintCategory category;

    @PastOrPresent(message = "Incident date cannot be in the future")
    private LocalDateTime incidentDate;

    private String additionalInfo;

    private String incidentLocation;

    private State state;

    private String district;

    private String policeStation;

    private String suspectName;
    private String suspectContact;
    private String suspectIdentificationDetails;
    private String suspectAdditionalInfo;
    @NotNull(message = "Citizen ID is required")
    private Long citizenId;
}
