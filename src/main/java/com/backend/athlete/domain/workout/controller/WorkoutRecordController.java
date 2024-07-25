package com.backend.athlete.domain.workout.controller;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.application.WorkoutRecordService;
import com.backend.athlete.domain.workout.dto.request.CreateWorkoutRecordRequest;
import com.backend.athlete.domain.workout.dto.response.*;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
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
    public ResponseEntity<PagedResponse<WorkoutRecordStatisticsResponse>> getMainWorkoutRecordsByDateRangeAndGender(
                                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                @RequestParam("gender") UserGenderType gender,
                                                @RequestParam("rating") String rating,
                                                @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
                                                @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage) {
        Page<WorkoutRecordStatisticsResponse> response = workoutRecordService.getMainWorkoutRecordsByDateRangeAndGender(date, gender, rating, page, perPage);
        PagedResponse<WorkoutRecordStatisticsResponse> pagedResponse = PagedResponse.fromPage(response);
        return ResponseEntity.status(HttpStatus.OK).body(pagedResponse);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyWorkoutResponse> getMonthlyWorkoutCounts(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {

        GetMonthlyWorkoutResponse response = workoutRecordService.getMonthlyWorkoutCounts(month, userPrincipal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetDailyWorkoutRecordResponse> getDailyWorkoutRecord(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {

        GetDailyWorkoutRecordResponse response = workoutRecordService.getDailyWorkoutRecord(date, userPrincipal);
        return ResponseEntity.ok(response);
    }

}
