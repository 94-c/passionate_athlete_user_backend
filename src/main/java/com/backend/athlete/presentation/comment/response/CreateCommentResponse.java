package com.backend.athlete.presentation.comment.response;

import com.backend.athlete.domain.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String noticeName;
    private String createdDate;

    public CreateCommentResponse(Long id, String content, String userName, String noticeName, String createdDate) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.noticeName = noticeName;
        this.createdDate = createdDate;
    }

    public static CreateCommentResponse fromEntity(Comment comment) {
        return new CreateCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getNotice().getTitle(),
                comment.getCreatedDate()
        );
    }
}