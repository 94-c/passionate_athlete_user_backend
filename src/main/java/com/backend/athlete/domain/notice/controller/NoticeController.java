package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.SaveNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.SaveNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.SearchNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.domain.notice.service.NoticeService;
import com.backend.athlete.global.constant.PageConstant;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<?> searchNotice(
            @RequestParam(defaultValue = "", required = false) String title,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        PageSearchNoticeRequest request = new PageSearchNoticeRequest(title, name);
        Page<SearchNoticeResponse> response = noticeService.searchNotices(request, page, perPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<SaveNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                           @RequestParam("notice") String noticeJson,
                                                           @RequestParam("file") MultipartFile file) {
        try {
            SaveNoticeRequest noticeRequest = objectMapper.readValue(noticeJson, SaveNoticeRequest.class);
            SaveNoticeResponse response = noticeService.saveNotice(userPrincipal, noticeRequest, file);
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
                                                             @RequestParam("notice") String noticeJson,
                                                             @RequestParam("file") MultipartFile file) {
        try {
            UpdateNoticeRequest noticeRequest = objectMapper.readValue(noticeJson, UpdateNoticeRequest.class);
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
}
