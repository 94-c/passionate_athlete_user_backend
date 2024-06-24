package com.backend.athlete.presentation.comment;

import com.backend.athlete.application.CommentService;
import com.backend.athlete.presentation.comment.request.CreateCommentRequest;
import com.backend.athlete.presentation.comment.request.UpdateCommentRequest;
import com.backend.athlete.presentation.comment.response.CreateCommentResponse;
import com.backend.athlete.presentation.comment.response.GetCommentResponse;
import com.backend.athlete.presentation.comment.response.UpdateCommentResponse;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PagedResponse<GetCommentResponse>> getComments(@PathVariable Long noticeId, Pageable pageable) {
        Page<GetCommentResponse> commentsPage = commentService.findAllComments(noticeId, pageable);
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
