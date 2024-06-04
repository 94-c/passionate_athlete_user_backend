package com.backend.athlete.presentation.notice;

import com.backend.athlete.application.LikeNoticeService;
import com.backend.athlete.presentation.notice.response.CreateLikeNoticeResponse;
import com.backend.athlete.presentation.notice.response.DeleteLikeNoticeResponse;
import com.backend.athlete.presentation.notice.response.GetLikeNoticeResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notices/{noticeId}/likes")
public class LikeNoticeController {
    private final LikeNoticeService likeNoticeService;

    public LikeNoticeController(LikeNoticeService likeNoticeService) {
        this.likeNoticeService = likeNoticeService;
    }

    @PostMapping
    public ResponseEntity<CreateLikeNoticeResponse> likeNotice(@PathVariable Long noticeId,
                                                               @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        CreateLikeNoticeResponse response = likeNoticeService.likeNotice(noticeId, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteLikeNoticeResponse> unlikeNotice(@PathVariable Long noticeId,
                                                                 @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        DeleteLikeNoticeResponse response = likeNoticeService.unlikeNotice(noticeId, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/count")
    public ResponseEntity<GetLikeNoticeResponse> getLikeCount(@PathVariable Long noticeId) {
        GetLikeNoticeResponse response = likeNoticeService.getLikeCount(noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
