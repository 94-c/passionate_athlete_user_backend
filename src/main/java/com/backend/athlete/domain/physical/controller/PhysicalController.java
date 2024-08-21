package com.backend.athlete.domain.physical.controller;

import com.backend.athlete.domain.physical.application.PhysicalService;
import com.backend.athlete.domain.physical.domain.Physical;
import com.backend.athlete.domain.physical.dto.request.CreatePhysicalRequest;
import com.backend.athlete.domain.physical.dto.request.UpdatePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.*;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/physicals")
@RequiredArgsConstructor
public class PhysicalController {
    private final PhysicalService physicalService;

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

    @PutMapping("/{physicalId}")
    public ResponseEntity<Void> updatePhysicalData(@PathVariable Long physicalId,
                                                   @RequestBody UpdatePhysicalRequest request) {
        physicalService.updatePhysical(physicalId, request);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<MonthlyFatChangeResponse> getMonthlyFatChange(
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        MonthlyFatChangeResponse response = physicalService.calculateMonthlyFatChange(userPrincipal, year, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/monthly-fat-change")
    public ResponseEntity<GetMonthlyFatChangeResponse> getMonthlyFatChangeRanking(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "gender", required = false) UserGenderType gender,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GetMonthlyFatChangeResponse response = physicalService.getMonthlyFatChangeRanking(year, month, branchId, gender, limit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
