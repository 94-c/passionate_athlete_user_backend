package com.backend.athlete.user.notice.controller;

import com.backend.athlete.user.notice.application.NoticeTypeService;
import com.backend.athlete.user.notice.dto.response.GetNoticeTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice-type")
public class NoticeTypeController {
    private final NoticeTypeService noticeTypeService;

    @GetMapping
    public ResponseEntity<List<GetNoticeTypeResponse>> getNoticeType() {
        List<GetNoticeTypeResponse> response = noticeTypeService.getNoticeTypes();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<GetNoticeTypeResponse>> getNoticeTypeByRole(@RequestParam String role) {
        List<GetNoticeTypeResponse> response = noticeTypeService.getNoticeTypesByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
