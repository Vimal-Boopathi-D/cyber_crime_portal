package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import com.cyber.portal.complaintAndFirManagement.repository.GACAppealRepository;
import com.cyber.portal.complaintAndFirManagement.service.GACAppealService;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GACAppealServiceImpl implements GACAppealService {
    private final GACAppealRepository gacAppealRepository;

    @Override
    public GACAppeal submitAppeal(GACAppeal appeal) {
        appeal.setStatus(IncidentStatus.UNDER_VERIFICATION);
        return gacAppealRepository.save(appeal);
    }

    @Override
    public Optional<GACAppeal> getAppeal(Long id) {
        return gacAppealRepository.findById(id);
    }
}
