package com.backend.athlete.domain.physical.controller;

import com.backend.athlete.domain.physical.dto.request.SavePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.SavePhysicalUserResponse;
import com.backend.athlete.domain.physical.service.PhysicalService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/physicals")
public class PhysicalController {

    private final PhysicalService physicalService;

    public PhysicalController(PhysicalService physicalService) {
        this.physicalService = physicalService;
    }

    /**
     * 1. 인바디 측정
     *  - default : user의 weight, height를 통하여 근육량과 체지방량 입력 후 bmi, 체지방률, 복부지방률, 기초대사량
     */
    @PostMapping
    public ResponseEntity<SavePhysicalUserResponse> savePhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                 @Valid @RequestBody SavePhysicalRequest dto) {
        SavePhysicalUserResponse savePhysical = physicalService.savePhysical(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePhysical);
    }


}
