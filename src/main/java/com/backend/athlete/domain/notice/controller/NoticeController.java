package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.dto.request.SaveNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.SaveNoticeResponse;
import com.backend.athlete.domain.notice.service.NoticeService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    public ResponseEntity<SaveNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                           @RequestPart("notice") SaveNoticeRequest noticeRequest,
                                                           @RequestPart("file") MultipartFile file) {
        try {
            SaveNoticeResponse response = noticeService.saveNotice(userPrincipal, noticeRequest, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
