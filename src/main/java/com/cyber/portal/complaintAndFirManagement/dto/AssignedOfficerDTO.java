package com.cyber.portal.complaintAndFirManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignedOfficerDTO {
    private Long officerId;
    private String officerName;
    private String rank;
    private String badgeNumber;

    private String policeStation;
    private String district;
    private String state;

    private String mobileNo;
    private String email;
}
