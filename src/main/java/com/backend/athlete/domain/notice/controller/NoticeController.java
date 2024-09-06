package com.backend.athlete.domain.notice.controller;

import com.backend.athlete.domain.notice.application.NoticeService;
import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.CreateNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.CreateNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/search")
    public ResponseEntity<Page<GetNoticeResponse>> searchNotices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam int page,
            @RequestParam int perPage,
            @RequestParam(required = false) Long kindId,
            @RequestParam boolean status) {

        PageSearchNoticeRequest request = new PageSearchNoticeRequest(name, title);
        Page<GetNoticeResponse> response = noticeService.searchNotices(request, page, perPage, kindId, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateNoticeResponse> createNotice(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestBody CreateNoticeRequest request) {
        CreateNoticeResponse response = noticeService.saveNotice(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateNoticeResponse> updateNotice(@PathVariable Long id,
                                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                             @RequestBody UpdateNoticeRequest request) {
        UpdateNoticeResponse response = noticeService.updateNotice(id, userPrincipal, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeResponse> getNotice(@PathVariable Long id) {
        GetNoticeResponse response = noticeService.getNotice(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetNoticeResponse>> getNoticesByType(
            @RequestParam(required = false) Long kindId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam boolean status
    ) {
        Page<GetNoticeResponse> response = noticeService.getNoticesByType(kindId, page, size, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        noticeService.deleteNotice(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }
}

