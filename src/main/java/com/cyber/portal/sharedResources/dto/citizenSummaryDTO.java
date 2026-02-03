package com.cyber.portal.sharedResources.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class citizenSummaryDTO {
    private long totalComplaints;
    private long pendingCases;
    private long closedCases;
}
