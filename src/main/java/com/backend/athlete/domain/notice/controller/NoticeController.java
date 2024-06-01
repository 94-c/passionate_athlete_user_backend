package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.dto.request.SaveNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.SaveNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.domain.notice.service.NoticeService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeService noticeService;
    private final ObjectMapper objectMapper;

    public NoticeController(NoticeService noticeService, ObjectMapper objectMapper) {
        this.noticeService = noticeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<SaveNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                           @RequestParam("notice") String noticeJson,
                                                           @RequestParam("file") MultipartFile file) {
        try {
            SaveNoticeRequest noticeRequest = objectMapper.readValue(noticeJson, SaveNoticeRequest.class);
            SaveNoticeResponse response = noticeService.saveNotice(userPrincipal, noticeRequest, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeResponse> getNotice(@PathVariable Long id) {
        GetNoticeResponse response = noticeService.getNotice(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoticeResponse> updateNotice(@PathVariable Long id,
                                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestParam("notice") String noticeJson,
                                                             @RequestParam("file") MultipartFile file) {
        try {
            UpdateNoticeRequest noticeRequest = objectMapper.readValue(noticeJson, UpdateNoticeRequest.class);
            UpdateNoticeResponse response = noticeService.updateNotice(id, userPrincipal, noticeRequest, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        noticeService.deleteNotice(id, userPrincipal);
        return ResponseEntity.ok().build();
    }
}
