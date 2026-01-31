package com.cyber.portal.citizenManagement.controller;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.service.CitizenService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citizens")
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenService citizenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Citizen>> register(@RequestBody Citizen citizen) {
        Citizen saved = citizenService.registerCitizen(citizen);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen registered successfully", saved));
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<ApiResponse<Citizen>> getCitizen(@PathVariable String loginId) {
        return citizenService.getCitizenByLoginId(loginId)
                .map(citizen -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen found", citizen)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Citizen not found", null)));
    }
}
