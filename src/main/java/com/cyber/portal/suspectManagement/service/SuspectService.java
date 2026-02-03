package com.cyber.portal.suspectManagement.service;

import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import com.cyber.portal.suspectManagement.entity.SuspectRegistry;

public interface SuspectService {

    SuspectRegistry searchSuspect(
            SuspectIdentifierType identifierType,
            String identifierValue
    );

    SuspectRegistry reportSuspect(
            SuspectIdentifierType identifierType,
            String identifierValue,
            State incidentState,
            String description,
            String evidencePath
    );

}
