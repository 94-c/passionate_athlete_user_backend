package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.workout.application.WorkoutRecordHistoryService;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutRecordAndHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workout-record-histories")
public class WorkoutRecordHistoryController {
    private final WorkoutRecordHistoryService workoutRecordHistoryService;

    @GetMapping("/{workoutRecordId}")
    public ResponseEntity<GetWorkoutRecordAndHistoryResponse> getRecordWithHistories(@PathVariable Long workoutRecordId) {
        GetWorkoutRecordAndHistoryResponse response = workoutRecordHistoryService.getRecordWithHistories(workoutRecordId);
        return ResponseEntity.ok(response);
    }
}
