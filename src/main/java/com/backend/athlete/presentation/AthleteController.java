package com.backend.athlete.presentation;

import com.backend.athlete.application.AthleteService;
import com.backend.athlete.presentation.request.CreateAthleteRequest;
import com.backend.athlete.presentation.response.CreateAthleteResponse;
import com.backend.athlete.presentation.response.GetDailyAthleteResponse;
import com.backend.athlete.presentation.response.GetMonthlyAthleteResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<CreateAthleteResponse> createAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @Valid @RequestBody CreateAthleteRequest dto) {
        CreateAthleteResponse createAthlete = athleteService.createAthlete(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAthlete);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetDailyAthleteResponse> getDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                   @RequestParam(name = "daily", required = false) LocalDate dailyDate) {

        GetDailyAthleteResponse getDailyAthlete = athleteService.getDailyAthlete(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(getDailyAthlete);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyAthleteResponse> getMonthlyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                       @RequestParam(name = "month") YearMonth month) {

        GetMonthlyAthleteResponse getMonthlyAthlete = athleteService.getMonthlyAthlete(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(getMonthlyAthlete);
    }

    @DeleteMapping("/daily/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Void> deleteDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                   @PathVariable Long id) {
        athleteService.deleteDailyAthleteById(userPrincipal, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
