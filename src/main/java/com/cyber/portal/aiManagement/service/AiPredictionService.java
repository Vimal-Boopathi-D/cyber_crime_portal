package com.cyber.portal.aiManagement.service;

import com.cyber.portal.sharedResources.enums.Label;

public interface AiPredictionService {
    public Label getIncidentLabel(String description);
}
