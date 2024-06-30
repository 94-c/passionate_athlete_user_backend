package com.backend.athlete.presentation.physicalInfo;

import com.backend.athlete.application.PhysicalInfoService;
import com.backend.athlete.presentation.physicalInfo.response.GetPhysicalInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/physical-info")
public class PhysicalInfoController {
    private final PhysicalInfoService physicalInfoService;

    public PhysicalInfoController(PhysicalInfoService physicalInfoService) {
        this.physicalInfoService = physicalInfoService;
    }

    @GetMapping
    public ResponseEntity<List<GetPhysicalInfoResponse>> getAllPhysicalInfo() {
        List<GetPhysicalInfoResponse> response = physicalInfoService.getPhysicalInfo();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}