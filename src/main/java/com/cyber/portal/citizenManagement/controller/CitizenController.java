package com.cyber.portal.citizenManagement.controller;

import com.cyber.portal.citizenManagement.dto.ComplaintHistoryDto;
import com.cyber.portal.citizenManagement.dto.LoginDto;
import com.cyber.portal.citizenManagement.entity.Admin;
import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.entity.PoliceStation;
import com.cyber.portal.citizenManagement.service.CitizenService;
import com.cyber.portal.complaintAndFirManagement.service.ComplaintService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenService citizenService;
    private final ComplaintService complaintService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Citizen>> register(@RequestBody Citizen citizen) {
        Citizen saved = citizenService.registerCitizen(citizen);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen registered successfully", saved));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Citizen>> getCitizen(@RequestBody LoginDto loginDto) {
        return citizenService.getCitizenByLoginId(loginDto.getEmail(), loginDto.getPassword())
                .map(citizen -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen found", citizen)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Citizen not found", null)));
    }

    @GetMapping("/officers")
    public ResponseEntity<ApiResponse<List<PoliceOfficer>>> getAllPoliceOfficers(){
        List<PoliceOfficer> officers = citizenService.getAllPoliceOfficers();
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Fetched all policeOfficers", officers));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PoliceStation>>> getAllPoliceStations(){
        List<PoliceStation> stations = citizenService.getAllPoliceStation();

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Fetched all police stations", stations));
    }

    @GetMapping("/complaint/history/{citizenId}")
    public ResponseEntity<ApiResponse<List<ComplaintHistoryDto>>> getCitizenHistory(@PathVariable("citizenId") Long citizenId) {
        List<ComplaintHistoryDto> history = complaintService.getCitizenComplaints(citizenId);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen history retrieved", history));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse<Admin>> getAdmin(@RequestBody LoginDto loginDto) {
        return citizenService.getAdminByLoginId(loginDto.getEmail(), loginDto.getPassword())
                .map(citizen -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Admin found", citizen)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Admin not found", null)));
    }
}

