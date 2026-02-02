package com.cyber.portal.citizenManagement.controller;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceStation;
import com.cyber.portal.citizenManagement.service.CitizenService;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Citizen>> register(@RequestBody Citizen citizen,
                                                         @RequestParam(required = false) Long stationId,
                                                         @RequestParam(required = false) String badgeNumber,
                                                         @RequestParam(required = false) String rank) {
        Citizen saved = citizenService.registerCitizen(citizen, stationId, badgeNumber, rank);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen registered successfully", saved));
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<Citizen>> getCitizen(@RequestParam String email, @RequestParam String password) {
        return citizenService.getCitizenByLoginId(email, password)
                .map(citizen -> ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Citizen found", citizen)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.of(HttpStatus.NOT_FOUND, "Citizen not found", null)));
    }

//    @GetMapping()
//    public ResponseEntity<ApiResponse<List<PoliceStation>>> getAllPoliceStations(){
//        List<PoliceStation> stations = citizenService.getAllPoliceStation();
//
//        return ResponseEntity.ok(
//                ApiResponse.success("",stations)
//        );
//    }


}

