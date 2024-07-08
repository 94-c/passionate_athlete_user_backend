package com.backend.athlete.domain.athlete.controller;

import com.backend.athlete.domain.athlete.application.AthleteService;
import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequest;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponse;
import com.backend.athlete.domain.athlete.dto.response.GetDailyAthleteResponse;
import com.backend.athlete.domain.athlete.dto.response.GetMonthlyAthleteResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/athletes")
@RequiredArgsConstructor
public class AthleteController {

    /**
     * 오늘 운동 기록 조회
     * 월별 운동 조회
     * 오늘 운동 기록 삭제
     */
    private final AthleteService athleteService;

    @GetMapping("/daily")
    public ResponseEntity<GetDailyAthleteResponse> getDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                   @RequestParam(name = "daily", required = false) LocalDate dailyDate) {

        GetDailyAthleteResponse response = athleteService.getDailyAthlete(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyAthleteResponse> getMonthlyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                       @RequestParam(name = "month") YearMonth month) {

        GetMonthlyAthleteResponse response = athleteService.getMonthlyAthlete(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/daily/{id}")
    public ResponseEntity<Void> deleteDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                   @PathVariable Long id) {
        athleteService.deleteDailyAthleteById(userPrincipal, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
