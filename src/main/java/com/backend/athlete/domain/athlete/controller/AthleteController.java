package com.backend.athlete.domain.athlete.controller;

import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequestDto;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponseDto;
import com.backend.athlete.domain.athlete.service.AthleteService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.OK).body(createAthlete);
    }

}
