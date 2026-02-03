package com.cyber.portal.suspectManagement.service;

import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import com.cyber.portal.suspectManagement.entity.SuspectRegistry;
import com.cyber.portal.suspectManagement.repository.SuspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class suspectServiceImpl implements SuspectService{

    private final SuspectRepository repository;

    @Override
    public SuspectRegistry searchSuspect(
            SuspectIdentifierType identifierType,
            String identifierValue) {

        return repository
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

        SuspectRegistry suspect = repository
                .findByIdentifierTypeAndIdentifierValue(identifierType, identifierValue)
                .orElse(null);
        if (suspect != null) {
            suspect.setReportCount(suspect.getReportCount() + 1);
            suspect.setIncidentState(incidentState);
            suspect.setDescription(description);
            suspect.setEvidencePath(evidencePath);
            // lastReportedAt auto-updated by @PreUpdate
            return repository.save(suspect);
        }

        SuspectRegistry newSuspect = SuspectRegistry.builder()
                .identifierType(identifierType)
                .identifierValue(identifierValue)
                .incidentState(incidentState)
                .description(description)
                .evidencePath(evidencePath)
                .build();
        return repository.save(newSuspect);
    }
}
