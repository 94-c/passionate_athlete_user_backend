package com.backend.athlete.user.notice.dto.response;

import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.user.domain.User;
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
