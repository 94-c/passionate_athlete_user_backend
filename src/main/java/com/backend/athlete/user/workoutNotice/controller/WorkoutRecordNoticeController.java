package com.backend.athlete.user.workoutNotice.controller;

import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.workoutNotice.application.WorkoutRecordNoticeService;
import com.backend.athlete.user.workoutNotice.dto.response.CreateWorkoutRecordNoticeResponse;
import com.backend.athlete.user.workoutNotice.dto.response.GetNonSharedWorkoutRecordResponse;
import com.backend.athlete.user.workoutNotice.dto.response.GetWorkoutRecordNoticeAndHistoryResponse;
import com.backend.athlete.user.workoutNotice.dto.response.GetWorkoutRecordNoticeResponse;
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
    @PostMapping("/{workoutRecordId}")
    public ResponseEntity<CreateWorkoutRecordNoticeResponse> createWorkoutRecordNotice(@PathVariable Long workoutRecordId,
                                                                                       @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        CreateWorkoutRecordNoticeResponse response = workoutRecordNoticeService.createWorkoutRecordNotice(workoutRecordId, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetWorkoutRecordNoticeResponse>> findAllWorkRecordNotices (
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        Page<GetWorkoutRecordNoticeResponse> response = workoutRecordNoticeService.findAllWorkRecordNotices(page, perPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetWorkoutRecordNoticeAndHistoryResponse> getWorkoutRecordNotice(@PathVariable Long id) {
        GetWorkoutRecordNoticeAndHistoryResponse response = workoutRecordNoticeService.getWorkRecordNotice(id);
        return ResponseEntity.ok(response);
    }
}
