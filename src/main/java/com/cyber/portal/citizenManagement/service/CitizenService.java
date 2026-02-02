package com.cyber.portal.citizenManagement.service;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.entity.PoliceStation;

import java.util.List;
import java.util.Optional;

public interface CitizenService {
    Citizen registerCitizen(Citizen citizen, Long stationId, String badgeNumber, String rank);
    Optional<Citizen> getCitizenByLoginId(String email, String password);
    List<PoliceStation> getAllPoliceStation();
    List<PoliceOfficer> getAllPoliceOfficers();
}
