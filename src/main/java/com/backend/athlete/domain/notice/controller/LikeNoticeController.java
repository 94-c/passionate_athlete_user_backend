package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.service.LikeNoticeService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
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
    public ResponseEntity<Void> likeNotice(@PathVariable Long noticeId, @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        likeNoticeService.likeNotice(noticeId, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikeNotice(@PathVariable Long noticeId, @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        likeNoticeService.unlikeNotice(noticeId, userPrincipal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long noticeId) {
        long likeCount = likeNoticeService.getLikeCount(noticeId);
        return ResponseEntity.ok(likeCount);
    }
}
