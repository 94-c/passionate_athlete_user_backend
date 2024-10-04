package com.backend.athlete.user.notice.dto.response;

import com.backend.athlete.user.notice.domain.NoticeType;
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
