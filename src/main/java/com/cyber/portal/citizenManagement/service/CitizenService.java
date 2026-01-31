package com.cyber.portal.citizenManagement.service;

import com.cyber.portal.citizenManagement.entity.Citizen;
import java.util.Optional;

public interface CitizenService {
    Citizen registerCitizen(Citizen citizen);
    Optional<Citizen> getCitizenByLoginId(String loginId);
}
