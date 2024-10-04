package com.backend.athlete.user.workoutNotice.controller;

import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.workoutNotice.application.WorkoutRecordNoticeCommentService;
import com.backend.athlete.user.workoutNotice.dto.request.CreateWorkoutRecordNoticeCommentRequest;
import com.backend.athlete.user.workoutNotice.dto.request.UpdateWorkoutRecordNoticeCommentRequest;
import com.backend.athlete.user.workoutNotice.dto.response.*;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workout-record-notice/{noticeId}/comments")
public class WorkoutRecordNoticeCommentController {
    private final WorkoutRecordNoticeCommentService workoutRecordNoticeCommentService;

    @GetMapping
    public ResponseEntity<PagedResponse<GetWorkoutRecordNoticeCommentResponse>> getWorkoutRecordNoticeComments(@PathVariable Long noticeId,
                                                                                                        @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
                                                                                                        @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage) {
        Page<GetWorkoutRecordNoticeCommentResponse> workoutRecordNoticeResponsePage = workoutRecordNoticeCommentService.findAllComments(noticeId, page, perPage);
        PagedResponse<GetWorkoutRecordNoticeCommentResponse> response = PagedResponse.fromPage(workoutRecordNoticeResponsePage);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateWorkoutRecordNoticeCommentResponse> createWorkoutRecordNoticeComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                                                     @PathVariable Long noticeId,
                                                                                                     @RequestBody CreateWorkoutRecordNoticeCommentRequest request) {
        CreateWorkoutRecordNoticeCommentResponse response = workoutRecordNoticeCommentService.createComment(userPrincipal, noticeId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateWorkoutRecordNoticeCommentResponse> updateWorkoutRecordNoticeComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                                                     @PathVariable Long id,
                                                                                                     @RequestBody UpdateWorkoutRecordNoticeCommentRequest request) {
        UpdateWorkoutRecordNoticeCommentResponse response = workoutRecordNoticeCommentService.updateComment(userPrincipal, id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutRecordNoticeComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                 @PathVariable Long id) {
        workoutRecordNoticeCommentService.deleteComment(userPrincipal, id);

        return ResponseEntity.noContent().build();
    }
}
