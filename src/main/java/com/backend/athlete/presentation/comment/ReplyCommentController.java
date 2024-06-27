package com.backend.athlete.presentation.comment;

import com.backend.athlete.application.ReplyCommentService;
import com.backend.athlete.presentation.comment.request.CreateReplyCommentRequest;
import com.backend.athlete.presentation.comment.response.CreateReplyCommentResponse;
import com.backend.athlete.presentation.comment.response.GetReplyCommentResponse;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments/{commentId}/replies")
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<GetReplyCommentResponse>> getReplyComment(@PathVariable Long commentId, Pageable pageable) {
        Page<GetReplyCommentResponse> replyCommentsPage = replyCommentService.findCommentByReply(commentId, pageable);
        PagedResponse<GetReplyCommentResponse> pagedResponse = PagedResponse.fromPage(replyCommentsPage);
        return ResponseEntity.ok(pagedResponse);
    }

    @PostMapping
    public ResponseEntity<CreateReplyCommentResponse> createReplyComment(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                         @PathVariable Long commentId,
                                                                         @RequestBody CreateReplyCommentRequest request) {
        CreateReplyCommentResponse response = replyCommentService.createReplyComment(userPrincipal, commentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
