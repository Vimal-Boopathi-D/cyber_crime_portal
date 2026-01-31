package com.cyber.portal.suspectManagement.repository;

import com.cyber.portal.suspectManagement.entity.SuspectRegistry;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SuspectRepository extends JpaRepository<SuspectRegistry, Long> {
    Optional<SuspectRegistry> findByIdentifierTypeAndIdentifierValue(SuspectIdentifierType type, String value);
}
