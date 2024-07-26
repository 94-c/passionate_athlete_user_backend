package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.workout.application.WorkoutStaticsService;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workout-statics")
@RequiredArgsConstructor
public class WorkoutStaticsController {
    private final WorkoutStaticsService workoutStaticsService;
    @GetMapping
    public ResponseEntity<GetWorkoutStatisticsResponse> getWorkoutStatistics(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetWorkoutStatisticsResponse response = workoutStaticsService.getWorkoutStatistics(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
