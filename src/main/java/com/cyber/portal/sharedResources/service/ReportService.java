package com.cyber.portal.sharedResources.service;

import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.ComplaintTimeline;
import com.cyber.portal.sharedResources.entity.FollowUpMessage;
import com.cyber.portal.sharedResources.repository.FollowUpMessageRepository;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintTimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ComplaintTimelineRepository timelineRepository;
    private final FollowUpMessageRepository messageRepository;

    public byte[] generateComplaintReport(Complaint complaint) {
        List<ComplaintTimeline> timeline = timelineRepository.findByComplaintIdOrderByUpdatedAtDesc(complaint.getId());
        List<FollowUpMessage> messages = messageRepository.findByComplaintIdOrderBySentAtAsc(complaint.getId());

        StringBuilder report = new StringBuilder();
        report.append("--- COMPLAINT REPORT ---\n");
        report.append("Acknowledgement No: ").append(complaint.getAcknowledgementNo()).append("\n");
        report.append("Category: ").append(complaint.getCategory()).append("\n");
        report.append("Status: ").append(complaint.getStatus()).append("\n");
        report.append("Created At: ").append(complaint.getCreatedAt()).append("\n\n");

        report.append("--- TRACKING TIMELINE ---\n");
        for (ComplaintTimeline t : timeline) {
            report.append("[").append(t.getUpdatedAt()).append("] ")
                  .append(t.getStatus()).append(": ")
                  .append(t.getRemarks()).append("\n");
        }

        report.append("\n--- COMMUNICATION LOGS ---\n");
        for (FollowUpMessage m : messages) {
            report.append("[").append(m.getSentAt()).append("] ")
                  .append(m.getSenderRole()).append(": ")
                  .append(m.getMessage()).append("\n");
        }

        // In a real implementation with iText, we would generate a proper PDF.
        // For now, we return the text report as a byte array for demonstration.
        return report.toString().getBytes(StandardCharsets.UTF_8);
    }
}
