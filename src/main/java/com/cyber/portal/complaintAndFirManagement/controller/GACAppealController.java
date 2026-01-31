package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.entity.GACAppeal;
import com.cyber.portal.complaintAndFirManagement.service.GACAppealService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appeals")
@RequiredArgsConstructor
public class GACAppealController {
    private final GACAppealService gacAppealService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<GACAppeal>> submitAppeal(@RequestBody GACAppeal appeal) {
        GACAppeal saved = gacAppealService.submitAppeal(appeal);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Appeal submitted successfully", saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GACAppeal>> getAppeal(@PathVariable Long id) {
        return gacAppealService.getAppeal(id)
                .map(appeal -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Appeal found", appeal)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Appeal not found", null)));
    }
}
