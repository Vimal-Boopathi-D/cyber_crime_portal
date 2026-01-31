package com.cyber.portal.citizenManagement.repository;

import com.cyber.portal.citizenManagement.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByLoginId(String loginId);
}
