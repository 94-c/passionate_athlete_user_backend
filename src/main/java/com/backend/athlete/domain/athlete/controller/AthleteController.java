package com.backend.athlete.domain.athlete.controller;

import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequestDto;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponseDto;
import com.backend.athlete.domain.athlete.dto.response.GetDailyAthleteResponseDto;
import com.backend.athlete.domain.athlete.dto.response.GetMonthlyAthleteResponseDto;
import com.backend.athlete.domain.athlete.service.AthleteService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/athletes")
public class AthleteController {

    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    /**
     * 1. 데일리 운동 기록 입력
     * 2. 데일리 운동 상세 보기
     * 3. 데일리 운동 기록 월별 조회 (페이징)
     * 4. 데일리 운동 기록 삭제
     */

    @PostMapping
    public ResponseEntity<CreateAthleteResponseDto> createAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                  @Valid @RequestBody CreateAthleteRequestDto dto) {
        CreateAthleteResponseDto createAthlete = athleteService.createAthlete(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAthlete);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetDailyAthleteResponseDto> getDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                      @RequestParam(name = "daily", required = false) LocalDate dailyDate) {

        GetDailyAthleteResponseDto getDailyAthlete = athleteService.getDailyAthlete(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(getDailyAthlete);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyAthleteResponseDto> getMonthlyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                          @RequestParam(name = "month") YearMonth month) {

        GetMonthlyAthleteResponseDto getMonthlyAthlete = athleteService.getMonthlyAthlete(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(getMonthlyAthlete);
    }
}
