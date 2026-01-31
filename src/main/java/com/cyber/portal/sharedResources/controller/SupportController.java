package com.cyber.portal.sharedResources.controller;

import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.entity.FollowUpMessage;
import com.cyber.portal.sharedResources.repository.FollowUpMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {
    private final FollowUpMessageRepository followUpMessageRepository;

    @GetMapping("/helpline")
    public ResponseEntity<ApiResponse<Map<String, String>>> getHelplineInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("helplineNumber", "1930");
        info.put("email", "support@cyberportal.gov");
        info.put("website", "https://cybercrime.gov.in");
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Helpline information retrieved", info));
    }

    @GetMapping("/faq")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getFAQ() {
        List<Map<String, String>> faqs = List.of(
            Map.of("question", "How do I report a cybercrime?", "answer", "Click on the Submit Complaint button and fill the form."),
            Map.of("question", "What is the 1930 helpline?", "answer", "It is the national helpline for reporting financial cybercrimes."),
            Map.of("question", "Can I track my complaint status?", "answer", "Yes, use your acknowledgement number in the 'Track Complaint' section.")
        );
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "FAQs retrieved", faqs));
    }

    @PostMapping("/messages")
    public ResponseEntity<ApiResponse<FollowUpMessage>> sendMessage(@RequestBody FollowUpMessage message) {
        FollowUpMessage saved = followUpMessageRepository.save(message);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Message sent successfully", saved));
    }

    @GetMapping("/messages/{complaintId}")
    public ResponseEntity<ApiResponse<List<FollowUpMessage>>> getMessages(@PathVariable Long complaintId) {
        List<FollowUpMessage> messages = followUpMessageRepository.findByComplaintIdOrderBySentAtAsc(complaintId);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Messages retrieved", messages));
    }
}
