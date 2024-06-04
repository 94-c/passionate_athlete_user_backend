package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNoticeCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String createdAt;

    public GetNoticeCommentResponse(Long id, String content, String userName, String createdAt) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
    }
    public static GetNoticeCommentResponse fromEntity(Comment comment) {
        return new GetNoticeCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getCreatedDate()
        );
    }
}
