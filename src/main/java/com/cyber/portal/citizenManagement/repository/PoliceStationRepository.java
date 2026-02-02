package com.cyber.portal.citizenManagement.repository;

import com.cyber.portal.citizenManagement.entity.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliceStationRepository extends JpaRepository<PoliceStation,Long> {
    PoliceStation getByStationId(Long stationId);
}
