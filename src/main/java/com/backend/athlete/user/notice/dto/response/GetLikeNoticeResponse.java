package com.backend.athlete.user.notice.dto.response;

import com.backend.athlete.user.notice.domain.Notice;
import lombok.Getter;

@Getter
public class GetLikeNoticeResponse {

    private String title;
    private String userName;
    private long likeCount;

    public GetLikeNoticeResponse(String title, String userName, long likeCount) {
        this.title = title;
        this.userName = userName;
        this.likeCount = likeCount;
    }

    public static GetLikeNoticeResponse fromEntity(Notice notice, long likeCount) {
        return new GetLikeNoticeResponse(
                notice.getTitle(),
                notice.getUser().getName(),
                likeCount
        );
    }
}
