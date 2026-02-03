package com.cyber.portal.sharedResources.service.impl;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import com.cyber.portal.sharedResources.service.NotificationService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ComplaintRepository complaintRepository;
    private final ComplaintTimelineRepository timelineRepository;

    @Override
    public void sendNotification(String to, String subject, String message) {
        log.info("Sending Notification to {}: [Subject: {}] [Message: {}]", to, subject, message);
    }

    @Override
    public void sendStatusUpdate(Long complaintId) {
        log.info("Triggering status update notification for complaint ID: {}", complaintId);

        complaintRepository.findById(complaintId).ifPresent(complaint -> {
            if (complaint.getCitizen() != null && complaint.getCitizen().getEmail() != null) {
                List<ComplaintTimeline> timeline = timelineRepository.findByComplaintIdOrderByUpdatedAtDesc(complaintId);
                if (!timeline.isEmpty()) {
                    ComplaintTimeline latest = timeline.get(0);
                    sendHtmlEmail(complaint, latest);
                }
            } else {
                log.warn("Citizen email not found for complaint ID: {}", complaintId);
            }
        });
    }

    private void sendHtmlEmail(Complaint complaint, ComplaintTimeline timeline) {
        try {
            Context context = new Context();
            context.setVariable("citizenName", complaint.getCitizen().getName());
            context.setVariable("ackNo", complaint.getAcknowledgementNo());
            context.setVariable("status", timeline.getStatus().name());
            context.setVariable("updatedAt", timeline.getUpdatedAt());
            context.setVariable("remarks", timeline.getRemarks());

            String htmlContent = templateEngine.process("emails/status-update-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("bharath2000e@gmail.com");
            helper.setTo(complaint.getCitizen().getEmail());
            helper.setSubject("IMPORTANT: Complaint Status Update - " + complaint.getAcknowledgementNo());
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Status update email sent successfully to {}", complaint.getCitizen().getEmail());
        } catch (Exception e) {
            log.error("Failed to send status update email: {}", e.getMessage());
        }
    }

    @Override
    public void sendSMS(String phoneNumber, String message) {
        log.info("Sending SMS to {}: {}", phoneNumber, message);
    }

    @Override
    public void sendInAppNotification(Long userId, String message) {
        log.info("Sending In-app notification to User {}: {}", userId, message);
    }
}
