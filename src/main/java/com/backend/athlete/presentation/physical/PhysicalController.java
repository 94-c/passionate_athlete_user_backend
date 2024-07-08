package com.backend.athlete.presentation.physical;

import com.backend.athlete.application.PhysicalService;
import com.backend.athlete.presentation.physical.request.CreatePhysicalRequest;
import com.backend.athlete.presentation.physical.response.*;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/physicals")
public class PhysicalController {

    private final PhysicalService physicalService;

    public PhysicalController(PhysicalService physicalService) {
        this.physicalService = physicalService;
    }

    @PostMapping
    public ResponseEntity<CreatePhysicalResponse> savePhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @Valid @RequestBody CreatePhysicalRequest request) {
        CreatePhysicalResponse response = physicalService.savePhysical(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/last")
    public ResponseEntity<LastGetPhysicalResponse> getLastPhysical() {
        LastGetPhysicalResponse response = physicalService.findLastPhysical();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetPhysicalResponse> getPhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                           @RequestParam(name = "daily", required = false) LocalDate dailyDate) {
        GetPhysicalResponse response = physicalService.getPhysical(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<PagedResponse<PagePhysicalResponse>> getAllPhysical(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                              @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
                                                                              @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage) {
        Page<PagePhysicalResponse> response = physicalService.getPhysicalData(userPrincipal, page, perPage);
        PagedResponse<PagePhysicalResponse> pagedResponse = PagedResponse.fromPage(response);
        return ResponseEntity.ok(pagedResponse);
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<GetPhysicalRankingResponse>> getRankingPhysical(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "gender") String gender,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        List<GetPhysicalRankingResponse> response = physicalService.getRankingPhysical(type, gender, limit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/monthly-fat-change")
    public ResponseEntity<MonthlyFatChangeResponse> getMonthlyFatChange(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        MonthlyFatChangeResponse response = physicalService.calculateMonthlyFatChange(userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
