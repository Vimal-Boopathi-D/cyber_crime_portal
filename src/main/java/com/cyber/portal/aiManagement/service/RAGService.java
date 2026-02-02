package com.cyber.portal.aiManagement.service;

import com.cyber.portal.sharedResources.enums.IncidentStatus;

public interface RAGService {
    String chatUsingRag(String message);
    String explainIncidentStatus(IncidentStatus status);

}
