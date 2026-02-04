package com.cyber.portal.complaintAndFirManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintMonthlyCountDto {
    private Integer year;
    private Integer month;
    private Long totalComplaints;

    public ComplaintMonthlyCountDto(Object year, Object month, Object totalComplaints) {
        this.year = year != null ? ((Number) year).intValue() : null;
        this.month = month != null ? ((Number) month).intValue() : null;
        this.totalComplaints = totalComplaints != null
                ? ((Number) totalComplaints).longValue()
                : 0L;
    }
}
