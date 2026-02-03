package com.cyber.portal.complaintAndFirManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GACAppealRequestDTO {

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;

    @NotNull(message = "Appeal reason is required")
    private String appealReason;
}

