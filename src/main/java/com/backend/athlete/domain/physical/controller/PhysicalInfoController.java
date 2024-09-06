package com.backend.athlete.domain.physical.controller;

import com.backend.athlete.domain.physical.application.PhysicalInfoService;
import com.backend.athlete.domain.physical.dto.response.GetPhysicalInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/physical-info")
@RequiredArgsConstructor
public class PhysicalInfoController {
    private final PhysicalInfoService physicalInfoService;

    @GetMapping
    public ResponseEntity<List<GetPhysicalInfoResponse>> getAllPhysicalInfo() {
        List<GetPhysicalInfoResponse> response = physicalInfoService.getPhysicalInfo();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
