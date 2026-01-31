package com.cyber.portal.sharedResources.repository;

import com.cyber.portal.sharedResources.entity.FollowUpMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowUpMessageRepository extends JpaRepository<FollowUpMessage, Long> {
    List<FollowUpMessage> findByComplaintIdOrderBySentAtAsc(Long complaintId);
}
