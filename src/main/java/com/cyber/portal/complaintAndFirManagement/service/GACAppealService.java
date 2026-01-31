package com.cyber.portal.complaintAndFirManagement.service;

import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import java.util.Optional;

public interface GACAppealService {
    GACAppeal submitAppeal(GACAppeal appeal);
    Optional<GACAppeal> getAppeal(Long id);
}
