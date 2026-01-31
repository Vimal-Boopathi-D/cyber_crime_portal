package com.cyber.portal.suspectManagement.controller;

import com.cyber.portal.suspectManagement.entity.SuspectRegistry;
import com.cyber.portal.suspectManagement.repository.SuspectRepository;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suspects")
@RequiredArgsConstructor
public class SuspectController {
    private final SuspectRepository suspectRepository;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SuspectRegistry>> search(
            @RequestParam SuspectIdentifierType type, 
            @RequestParam String value) {
        return suspectRepository.findByIdentifierTypeAndIdentifierValue(type, value)
                .map(suspect -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Suspect found", suspect)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Suspect not found", null)));
    }
}
