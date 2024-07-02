package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.notice.NoticeType;
import lombok.Getter;

@Getter
public class GetNoticeTypeResponse {
    private Long id;
    private String type;
    private String role;

    public GetNoticeTypeResponse(Long id, String type, String role) {
        this.id = id;
        this.type = type;
        this.role = role;
    }

    public static GetNoticeTypeResponse fromEntity(NoticeType noticeType) {
        return new GetNoticeTypeResponse(noticeType.getId(), noticeType.getType(), noticeType.getRole());
    }
}
