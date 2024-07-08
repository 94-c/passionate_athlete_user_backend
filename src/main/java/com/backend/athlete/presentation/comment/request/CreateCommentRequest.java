package com.backend.athlete.presentation.comment.request;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommentRequest {
    @NotNull(message = "댓글 내용을 입력 해주세요.")
    private String content;

    protected CreateCommentRequest() {}

    public CreateCommentRequest(String content) {
        this.content = content;
    }

    public static Comment toEntity(CreateCommentRequest request, Notice notice, User user) {
        return new Comment(user, notice, request.getContent());
    }

}


