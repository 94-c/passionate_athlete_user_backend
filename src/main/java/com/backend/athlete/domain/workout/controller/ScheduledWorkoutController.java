package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.workout.application.ScheduledWorkoutService;
import com.backend.athlete.domain.workout.dto.request.CreateScheduledWorkoutRequest;
import com.backend.athlete.domain.workout.dto.response.CreateScheduledWorkoutResponse;
import com.backend.athlete.domain.workout.dto.response.GetScheduledWorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/date")
    public ResponseEntity<List<GetScheduledWorkoutResponse>> getScheduledWorkoutsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<GetScheduledWorkoutResponse> response = scheduledWorkoutService.getScheduledWorkoutsByDate(date);
        return ResponseEntity.ok(response);
    }
}
