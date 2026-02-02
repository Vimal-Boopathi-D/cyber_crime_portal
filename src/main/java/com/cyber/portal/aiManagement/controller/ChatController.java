package com.cyber.portal.aiManagement.controller;

import com.cyber.portal.aiManagement.dto.MessageDto;
import com.cyber.portal.aiManagement.dto.Response;
import com.cyber.portal.aiManagement.facade.ChatFacade;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatFacade chatFacade;
    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<Response>> chat(@RequestBody MessageDto message) {

        System.out.println("triggered");
        log.info("Received chat request: {}", message);
        ApiResponse<Response> response = chatFacade.chat(message);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<String>> message(@RequestBody MessageDto dto) {

        ApiResponse<String> response = chatFacade.routeMessage(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/status/tracking/updates")
    public ResponseEntity<ApiResponse<String>> IncidentStatusContext(
            @RequestParam("status") IncidentStatus status
    ) {
        ApiResponse<String> response = chatFacade.trackUpdates(status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
