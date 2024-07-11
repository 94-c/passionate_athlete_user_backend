package com.backend.athlete.domain.execise.controller;

import com.backend.athlete.domain.execise.application.WorkoutService;
import com.backend.athlete.domain.execise.dto.request.CreateWorkoutRequest;
import com.backend.athlete.domain.execise.dto.response.GetWorkoutResponse;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<PagedResponse<GetWorkoutResponse>> getAllWorkoutsPaged(
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage,
            @RequestParam(defaultValue = "") String title) {
        PagedResponse<GetWorkoutResponse> response = workoutService.getAllWorkoutsPaged(title, page, perPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetWorkoutResponse> createWorkout(@RequestBody CreateWorkoutRequest request) {
        GetWorkoutResponse response = workoutService.createWorkout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetWorkoutResponse> getWorkout(@PathVariable Long id) {
        GetWorkoutResponse response = workoutService.getWorkoutById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetWorkoutResponse> updateWorkout(@PathVariable Long id,
                                                               @RequestBody CreateWorkoutRequest request) {
        GetWorkoutResponse response = workoutService.updateWorkout(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

}
