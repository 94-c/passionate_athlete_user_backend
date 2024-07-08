package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

@Getter
public class DeleteLikeNoticeResponse {
    private Long noticeId;
    private String noticeTitle;
    private String userName;
    private Long likeCount;

    public DeleteLikeNoticeResponse(Long noticeId, String noticeTitle, String userName, Long likeCount) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.userName = userName;
        this.likeCount = likeCount;
    }

    public static DeleteLikeNoticeResponse fromEntity(Notice notice, User user, Long likeCount) {
        return new DeleteLikeNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                user.getName(),
                likeCount
        );
    }

}
