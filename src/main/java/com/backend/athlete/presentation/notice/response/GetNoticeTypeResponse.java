package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.notice.NoticeType;
import lombok.Getter;

@Getter
public class GetNoticeTypeResponse {
    private Long id;
    private String type;

    public GetNoticeTypeResponse(Long id, String type) {
        this.id = id;
        this.type = type;
    }
    public static GetNoticeTypeResponse fromEntity(NoticeType noticeType) {
        return new GetNoticeTypeResponse(noticeType.getId(), noticeType.getType());
    }
}
