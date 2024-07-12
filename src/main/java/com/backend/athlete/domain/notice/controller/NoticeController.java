package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.application.NoticeService;
import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.CreateNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.PageSearchNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.CreateNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.support.constant.PageConstant;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/search")
    public ResponseEntity<Page<PageSearchNoticeResponse>> searchNotices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam int page,
            @RequestParam int perPage,
            @RequestParam(required = false) Long kindId,
            @RequestParam boolean status) {

        PageSearchNoticeRequest request = new PageSearchNoticeRequest(name, title);
        Page<PageSearchNoticeResponse> response = noticeService.searchNotices(request, page, perPage, kindId, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestPart("noticeJson") String noticeJson) {
        try {
            CreateNoticeRequest noticeRequest = new ObjectMapper().readValue(noticeJson, CreateNoticeRequest.class);
            CreateNoticeResponse response = noticeService.saveNotice(userPrincipal, noticeRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoticeResponse> updateNotice(@PathVariable Long id,
                                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestPart("noticeJson") String noticeJson) {
        try {
            UpdateNoticeRequest noticeRequest = new ObjectMapper().readValue(noticeJson, UpdateNoticeRequest.class);
            UpdateNoticeResponse response = noticeService.updateNotice(id, userPrincipal, noticeRequest);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeResponse> getNotice(@PathVariable Long id) {
        GetNoticeResponse response = noticeService.getNotice(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetNoticeResponse>> getAllNotices() {
        List<GetNoticeResponse> response = noticeService.getAllNotices();
        return ResponseEntity.ok(response);
    }

}

