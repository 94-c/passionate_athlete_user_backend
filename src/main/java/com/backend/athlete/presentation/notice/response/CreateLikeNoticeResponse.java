package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.notice.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateLikeNoticeResponse {

    private Long noticeId;
    private String noticeTitle;
    private String userName;
    private Long likeCount;

    public CreateLikeNoticeResponse(Long noticeId, String noticeTitle, String userName, Long likeCount) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.userName = userName;
        this.likeCount = likeCount;
    }

    public static CreateLikeNoticeResponse fromEntity(Like like, Long likeCount) {
        return new CreateLikeNoticeResponse(
                like.getNotice().getId(),
                like.getNotice().getTitle(),
                like.getUser().getName(),
                likeCount
        );
    }
}
