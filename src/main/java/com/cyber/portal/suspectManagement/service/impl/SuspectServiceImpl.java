package com.cyber.portal.suspectManagement.service.impl;

import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import com.cyber.portal.suspectManagement.entity.SuspectRegistry;
import com.cyber.portal.suspectManagement.repository.SuspectRepository;
import com.cyber.portal.suspectManagement.service.SuspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuspectServiceImpl implements SuspectService {
    private final SuspectRepository suspectRepository;

    @Override
    public List<SuspectRegistry> getAllSuspect() {
        return suspectRepository.findAll();
    }

    @Override
    public SuspectRegistry searchSuspect(
            SuspectIdentifierType identifierType,
            String identifierValue) {

        return suspectRepository
                .findByIdentifierTypeAndIdentifierValue(identifierType, identifierValue)
                .orElse(null);
    }

    @Override
    public SuspectRegistry reportSuspect(
            SuspectIdentifierType identifierType,
            String identifierValue,
            State incidentState,
            String description,
            String evidencePath) {

        SuspectRegistry suspect = suspectRepository
                .findByIdentifierTypeAndIdentifierValue(identifierType, identifierValue)
                .orElse(null);
        if (suspect != null) {
            suspect.setReportCount(suspect.getReportCount() + 1);
            suspect.setIncidentState(incidentState);
            suspect.setDescription(description);
            suspect.setEvidencePath(evidencePath);
            // lastReportedAt auto-updated by @PreUpdate
            return suspectRepository.save(suspect);
        }

        SuspectRegistry newSuspect = SuspectRegistry.builder()
                .identifierType(identifierType)
                .identifierValue(identifierValue)
                .incidentState(incidentState)
                .description(description)
                .evidencePath(evidencePath)
                .build();
        return suspectRepository.save(newSuspect);
    }
}
