package com.backend.athlete.presentation.notice;

import com.backend.athlete.application.NoticeTypeService;
import com.backend.athlete.presentation.notice.response.GetNoticeTypeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notice-type")
public class NoticeTypeController {

    private final NoticeTypeService noticeTypeService;

    public NoticeTypeController(NoticeTypeService noticeTypeService) {
        this.noticeTypeService = noticeTypeService;
    }

    @GetMapping
    public ResponseEntity<List<GetNoticeTypeResponse>> getNoticeType() {
        List<GetNoticeTypeResponse> response = noticeTypeService.getNoticeTypes();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
