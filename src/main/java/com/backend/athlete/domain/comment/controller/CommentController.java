package com.backend.athlete.domain.comment.controller;

import com.backend.athlete.domain.comment.application.CommentService;
import com.backend.athlete.domain.comment.dto.request.CreateCommentRequest;
import com.backend.athlete.domain.comment.dto.request.UpdateCommentRequest;
import com.backend.athlete.domain.comment.dto.response.CreateCommentResponse;
import com.backend.athlete.domain.comment.dto.response.GetCommentResponse;
import com.backend.athlete.domain.comment.dto.response.UpdateCommentResponse;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.constant.PageConstant;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notices/{noticeId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<GetCommentResponse>> getComments(@PathVariable Long noticeId,
                                                                         @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
                                                                         @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage) {
        Page<GetCommentResponse> commentsPage = commentService.findAllComments(noticeId, page, perPage);
        PagedResponse<GetCommentResponse> pagedResponse = PagedResponse.fromPage(commentsPage);
        return ResponseEntity.ok(pagedResponse);
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @PathVariable Long noticeId,
                                                               @RequestBody CreateCommentRequest request) {
        CreateCommentResponse response = commentService.createComment(userPrincipal, noticeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @PathVariable Long id,
                                                               @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse response = commentService.updateComment(userPrincipal, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long noticeId, @PathVariable Long commentId) {
        GetCommentResponse response = commentService.getComment(noticeId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                              @PathVariable Long id) {
        commentService.deleteComment(userPrincipal, id);
        return ResponseEntity.ok().build();
    }


}
