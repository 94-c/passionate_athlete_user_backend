package com.backend.athlete.presentation.athlete;

import com.backend.athlete.application.AthleteService;
import com.backend.athlete.presentation.athlete.request.CreateAthleteRequest;
import com.backend.athlete.presentation.athlete.response.CreateAthleteResponse;
import com.backend.athlete.presentation.athlete.response.GetDailyAthleteResponse;
import com.backend.athlete.presentation.athlete.response.GetMonthlyAthleteResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
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
    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('MANAGER')")
    public ResponseEntity<CreateAthleteResponse> createAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @Valid @RequestBody CreateAthleteRequest request) {
        CreateAthleteResponse response = athleteService.createAthlete(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Void> deleteDailyAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                   @PathVariable Long id) {
        athleteService.deleteDailyAthleteById(userPrincipal, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
