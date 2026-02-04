package com.cyber.portal.complaintAndFirManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignedOfficerDTO {
    private Long id;
    private String Name;
    private String rank;
    private String state;
    private String officerCode;
}
