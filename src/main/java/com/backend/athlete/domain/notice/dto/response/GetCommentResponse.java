package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String createdAt;

    public GetCommentResponse(Long id, String content, String userName, String createdAt) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
    }
    public static GetCommentResponse fromEntity(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getCreatedDate()
        );
    }
}
