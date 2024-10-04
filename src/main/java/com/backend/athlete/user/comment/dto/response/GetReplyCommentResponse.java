package com.backend.athlete.user.comment.dto.response;

import com.backend.athlete.user.comment.domain.Comment;
import lombok.Getter;
@Getter
public class GetReplyCommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String createdDate;
    private String modifiedDate;

    public GetReplyCommentResponse(Long id, String content, Long userId, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static GetReplyCommentResponse fromEntity(Comment comment) {
        return new GetReplyCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }

}
