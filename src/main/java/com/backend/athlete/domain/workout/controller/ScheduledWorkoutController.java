package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.workout.application.ScheduledWorkoutService;
import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import com.backend.athlete.domain.workout.domain.WorkoutRating;
import com.backend.athlete.domain.workout.dto.request.CreateScheduledWorkoutRequest;
import com.backend.athlete.domain.workout.dto.response.CreateScheduledWorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scheduled-workouts")
@RequiredArgsConstructor
public class ScheduledWorkoutController {

    private final ScheduledWorkoutService scheduledWorkoutService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreateScheduledWorkoutResponse> createScheduledWorkout(@RequestBody CreateScheduledWorkoutRequest request) {
        CreateScheduledWorkoutResponse response = scheduledWorkoutService.saveScheduledWorkout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
