package com.cyber.portal.complaintAndFirManagement.controller;

import com.cyber.portal.complaintAndFirManagement.dto.GACAppealRequestDTO;
import com.cyber.portal.complaintAndFirManagement.dto.GACAppealResponseDTO;
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
    public ResponseEntity<ApiResponse<Void>> submitAppeal(
            @RequestBody GACAppealRequestDTO dto) {
        gacAppealService.submitAppeal(dto);
        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "Appeal submitted successfully", null)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GACAppealResponseDTO>> getAppeal(@PathVariable Long id) {
        GACAppealResponseDTO responseDTO= gacAppealService.getAppeal(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Appeal submitted successfully", responseDTO));
    }

}
