package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
