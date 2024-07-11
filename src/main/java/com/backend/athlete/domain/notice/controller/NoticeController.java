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
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<?> searchNotice(
            @RequestParam(defaultValue = "", required = false) String title,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage,
            @RequestParam(required = false) Long kindId,
            @RequestParam(defaultValue = "true") boolean status
    ) {
        PageSearchNoticeRequest request = new PageSearchNoticeRequest(title, name);
        Page<PageSearchNoticeResponse> response = noticeService.searchNotices(request, page, perPage, kindId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<CreateNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestParam("notice") String notice,
                                                             @RequestParam("file") MultipartFile file) {
        try {
            CreateNoticeRequest noticeRequest = objectMapper.readValue(notice, CreateNoticeRequest.class);
            CreateNoticeResponse response = noticeService.saveNotice(userPrincipal, noticeRequest, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeResponse> getNotice(@PathVariable Long id) {
        GetNoticeResponse response = noticeService.getNotice(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoticeResponse> updateNotice(@PathVariable Long id,
                                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestParam("notice") String notice,
                                                             @RequestParam("file") MultipartFile file) {
        try {
            UpdateNoticeRequest noticeRequest = objectMapper.readValue(notice, UpdateNoticeRequest.class);
            UpdateNoticeResponse response = noticeService.updateNotice(id, userPrincipal, noticeRequest, file);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        noticeService.deleteNotice(id, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/is-active")
    public ResponseEntity<GetNoticeResponse> setStatus(@PathVariable Long id,
                                                       @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetNoticeResponse response = noticeService.setStatus(id, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
