package com.backend.athlete.presentation.exercise;

import com.backend.athlete.application.WorkoutService;
import com.backend.athlete.presentation.exercise.request.CreateWorkoutRequest;
import com.backend.athlete.presentation.exercise.response.CreateWorkoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreateWorkoutResponse> createWorkout(@RequestBody CreateWorkoutRequest request) {
        CreateWorkoutResponse response = workoutService.createWorkout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
