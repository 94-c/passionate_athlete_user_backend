package com.backend.athlete.presentation;

import com.backend.athlete.application.CommentService;
import com.backend.athlete.presentation.request.CreateCommentRequest;
import com.backend.athlete.presentation.request.UpdateCommentRequest;
import com.backend.athlete.presentation.response.CreateCommentResponse;
import com.backend.athlete.presentation.response.GetCommentResponse;
import com.backend.athlete.presentation.response.UpdateCommentResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
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

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long id) {
        GetCommentResponse response = commentService.getComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                              @PathVariable Long id) {
        commentService.deleteComment(userPrincipal, id);
        return ResponseEntity.ok().build();
    }
}