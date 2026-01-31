package com.cyber.portal.volunteerManagement.controller;

import com.cyber.portal.volunteerManagement.entity.Volunteer;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerController {
    private final VolunteerRepository volunteerRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Volunteer>> register(@RequestBody Volunteer volunteer) {
        Volunteer saved = volunteerRepository.save(volunteer);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Volunteer registered successfully", saved));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Volunteer>>> list() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Volunteers retrieved", volunteers));
    }
}
