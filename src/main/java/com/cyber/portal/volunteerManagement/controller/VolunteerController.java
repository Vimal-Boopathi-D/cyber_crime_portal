package com.cyber.portal.volunteerManagement.controller;

import com.cyber.portal.volunteerManagement.dto.VolunteerRegistrationDto;
import com.cyber.portal.volunteerManagement.entity.Volunteer;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.volunteerManagement.service.VolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerController {
    private final VolunteerRepository volunteerRepository;
    private final VolunteerService volunteerService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> registerVolunteer(
            @RequestPart("data") @Valid VolunteerRegistrationDto dto,
            @RequestPart("resume") MultipartFile resume,
            @RequestPart("photo") MultipartFile photo
    ) throws IOException {

        volunteerService.register(dto, resume, photo);
        return ResponseEntity.status(201).body(ApiResponse.of(HttpStatus.CREATED, "Volunteer registered successfully", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Volunteer>>> list() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Volunteers retrieved", volunteers));
    }
}
