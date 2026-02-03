package com.cyber.portal.suspectManagement.controller;

import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.suspectManagement.entity.SuspectRegistry;
import com.cyber.portal.suspectManagement.repository.SuspectRepository;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import com.cyber.portal.suspectManagement.service.SuspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/suspects")
@RequiredArgsConstructor
public class SuspectController {
    private final SuspectService suspectService;

//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<SuspectRegistry>> search(
//            @RequestParam SuspectIdentifierType type,
//            @RequestParam String value) {
//        return suspectRepository.findByIdentifierTypeAndIdentifierValue(type, value)
//                .map(suspect -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Suspect found", suspect)))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Suspect not found", null)));
//    }



    @GetMapping("/search")
    public ResponseEntity<?> searchSuspect(
            @RequestParam SuspectIdentifierType identifierType,
            @RequestParam String identifierValue) {
        SuspectRegistry suspect =
                suspectService.searchSuspect(identifierType, identifierValue);
        if (suspect == null) {
            return ResponseEntity.ok(
                    Map.of("found", false, "message", "No suspect found"));
        }
        return ResponseEntity.ok(Map.of("found", true, "suspect", suspect));}

    @PostMapping("/report")
    public ResponseEntity<?> reportSuspect(
            @RequestParam SuspectIdentifierType identifierType,
            @RequestParam String identifierValue,
            @RequestParam State incidentState,
            @RequestParam String description,
            @RequestParam(required = false) String evidencePath) {
        SuspectRegistry suspect = suspectService.reportSuspect(identifierType, identifierValue, incidentState, description, evidencePath);

        return ResponseEntity.ok(Map.of("message", "Suspect reported successfully", "suspect", suspect));
    }







}
