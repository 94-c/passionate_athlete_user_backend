package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.workout.application.WorkoutStaticsService;
import com.backend.athlete.domain.workout.dto.response.GetExerciseTypeLastWeightResponse;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/last-weights")
    public ResponseEntity<List<GetExerciseTypeLastWeightResponse>> getLastWeightsByExerciseType(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                                                @RequestParam ExerciseType exerciseType) {
        List<GetExerciseTypeLastWeightResponse> response = workoutStaticsService.getLastWeightsByExerciseType(userPrincipal, exerciseType);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
