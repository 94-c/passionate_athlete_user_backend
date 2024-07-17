package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.application.WorkoutRecordService;
import com.backend.athlete.domain.workout.dto.request.CreateWorkoutRecordRequest;
import com.backend.athlete.domain.workout.dto.response.CreateWorkoutRecordResponse;
import com.backend.athlete.domain.workout.dto.response.WorkoutRecordStatisticsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workout-records")
@RequiredArgsConstructor
public class WorkoutRecordController {
    private final WorkoutRecordService workoutRecordService;

    @PostMapping
    public ResponseEntity<CreateWorkoutRecordResponse> createWorkoutRecord(@Valid @RequestBody CreateWorkoutRecordRequest request, @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        CreateWorkoutRecordResponse response = workoutRecordService.saveWorkoutRecord(request, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<WorkoutRecordStatisticsResponse>> getMainWorkoutRecordsByDateRangeAndGender(
                                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                @RequestParam("gender") UserGenderType gender) {
        List<WorkoutRecordStatisticsResponse> response = workoutRecordService.getMainWorkoutRecordsByDateRangeAndGender(date, gender);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
