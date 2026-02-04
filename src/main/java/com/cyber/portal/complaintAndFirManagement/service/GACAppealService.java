package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.dto.GACAppealRequestDTO;
import com.cyber.portal.complaintAndFirManagement.dto.GACAppealResponseDTO;
import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import java.util.Optional;

public interface GACAppealService {
    void submitAppeal(GACAppealRequestDTO dto);
    GACAppealResponseDTO getAppeal(Long id);
}
