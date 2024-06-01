package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String content;
    private String userName;

    public CommentResponse(Long id, String content, String userName, String createdDate) {
        this.id = id;
        this.content = content;
        this.userName = userName;
    }

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getCreatedDate()
        );
    }
}
