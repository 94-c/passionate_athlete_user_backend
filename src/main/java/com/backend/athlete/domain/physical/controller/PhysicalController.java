package com.backend.athlete.domain.physical.controller;

import com.backend.athlete.domain.physical.dto.request.SavePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.GetPhysicalResponse;
import com.backend.athlete.domain.physical.dto.response.SavePhysicalResponse;
import com.backend.athlete.domain.physical.service.PhysicalService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/physicals")
public class PhysicalController {

    private final PhysicalService physicalService;

    public PhysicalController(PhysicalService physicalService) {
        this.physicalService = physicalService;
    }

    /**
     * 1. 인바디 측정
     * 2. 당일 인바딩 조회
     * 3. 인바디 전체 조회 (페이징, 차감순)
     */
    @PostMapping
    public ResponseEntity<SavePhysicalResponse> savePhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @Valid @RequestBody SavePhysicalRequest dto) {
        SavePhysicalResponse savePhysical = physicalService.savePhysical(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePhysical);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetPhysicalResponse> getPhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                           @RequestParam(name = "daily", required = false) LocalDate dailyDate) {
        GetPhysicalResponse getPhysical = physicalService.getPhysical(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(getPhysical);
    }


}
