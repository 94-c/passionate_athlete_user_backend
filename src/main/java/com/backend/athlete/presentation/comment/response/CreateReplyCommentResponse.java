package com.backend.athlete.presentation.comment.response;

import com.backend.athlete.domain.comment.Comment;
import lombok.Getter;

@Getter
public class CreateReplyCommentResponse {
    private Long commentId;
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String noticeName;
    private String createdDate;

    public CreateReplyCommentResponse(Long commentId, Long id, String content, Long userId, String userName, String noticeName, String createdDate) {
        this.commentId = commentId;
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.noticeName = noticeName;
        this.createdDate = createdDate;
    }

    public static CreateReplyCommentResponse fromEntity(Comment comment) {
        return new CreateReplyCommentResponse(
                comment.getParent().getId(),
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }

}
