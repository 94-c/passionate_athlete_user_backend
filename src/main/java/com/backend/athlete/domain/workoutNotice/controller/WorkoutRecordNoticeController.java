package com.backend.athlete.domain.workoutNotice.controller;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutRecordAndHistoryResponse;
import com.backend.athlete.domain.workoutNotice.application.WorkoutRecordNoticeService;
import com.backend.athlete.domain.workoutNotice.dto.response.CreateWorkoutRecordNoticeResponse;
import com.backend.athlete.domain.workoutNotice.dto.response.GetNonSharedWorkoutRecordResponse;
import com.backend.athlete.support.constant.PageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workout-record-notice")
@RequiredArgsConstructor
public class WorkoutRecordNoticeController {
    private final WorkoutRecordNoticeService workoutRecordNoticeService;

    @GetMapping("/non-shared")
    public ResponseEntity<Page<GetNonSharedWorkoutRecordResponse>> getNonSharedWorkoutRecords(
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        Page<GetNonSharedWorkoutRecordResponse> response = workoutRecordNoticeService.getNonSharedWorkoutRecords(userPrincipal, page, perPage);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<CreateWorkoutRecordNoticeResponse> createWorkoutRecordNotice(Long workoutRecordId,
                                                                                       @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        CreateWorkoutRecordNoticeResponse response = workoutRecordNoticeService.createWorkoutRecordNotice(workoutRecordId, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
