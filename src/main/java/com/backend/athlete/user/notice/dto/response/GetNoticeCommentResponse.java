package com.backend.athlete.user.notice.dto.response;

import com.backend.athlete.user.comment.domain.Comment;
import lombok.Getter;

@Getter
public class GetNoticeCommentResponse {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String createdAt;

    public GetNoticeCommentResponse(Long id, String content, Long userId, String userName, String createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public static GetNoticeCommentResponse fromEntity(Comment comment) {
        return new GetNoticeCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedDate()
        );
    }
}
