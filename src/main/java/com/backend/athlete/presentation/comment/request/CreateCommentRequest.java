package com.backend.athlete.presentation.comment.request;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommentRequest {
    private Long parentId;
    @NotNull(message = "댓글 내용을 입력 해주세요.")
    private String content;

    public CreateCommentRequest(Long parentId, String content) {
        this.parentId = parentId;
        this.content = content;
    }

    public static Comment toEntity(CreateCommentRequest request, Notice notice, User user, Comment parent) {
        return new Comment(user, notice, parent, request.getContent());
    }

}


