package com.cyber.portal.sharedResources.service;

import com.cyber.portal.sharedResources.enums.IncidentStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AITrackingService {

    public Map<String, String> getPredictiveUpdate(IncidentStatus status) {
        Map<String, String> update = new HashMap<>();
        
        switch (status) {
            case SUBMITTED:
                update.put("summary", "Your complaint has been successfully queued for initial review.");
                update.put("nextStep", "Assignment to a verification officer.");
                update.put("estimatedTime", "24-48 hours");
                break;
            case RECEIVED:
                update.put("summary", "An officer has received your complaint and is checking the initial details.");
                update.put("nextStep", "Evidence verification.");
                update.put("estimatedTime", "1-2 days");
                break;
            case EVIDENCE_VERIFIED:
                update.put("summary", "All provided evidence has been verified for authenticity.");
                update.put("nextStep", "Assignment to the local police station.");
                update.put("estimatedTime", "24 hours");
                break;
            case ASSIGNED_TO_POLICE:
                update.put("summary", "A local police officer has been assigned to your case.");
                update.put("nextStep", "Generation of FIR if applicable.");
                update.put("estimatedTime", "2-3 days");
                break;
            case FIR_GENERATED:
                update.put("summary", "FIR has been registered. Official investigation is now active.");
                update.put("nextStep", "Field investigation and suspect identification.");
                update.put("estimatedTime", "5-7 days");
                break;
            case INVESTIGATING:
                update.put("summary", "Active investigation is ongoing. Police are pursuing leads.");
                update.put("nextStep", "Follow-up requests or final resolution.");
                update.put("estimatedTime", "Variable");
                break;
            case CLOSED:
                update.put("summary", "Your case has been resolved and closed.");
                update.put("nextStep", "None");
                update.put("estimatedTime", "Completed");
                break;
            default:
                update.put("summary", "Your complaint is being processed.");
                update.put("nextStep", "Checking internal systems.");
                update.put("estimatedTime", "Depends on complexity");
        }
        
        return update;
    }
}
