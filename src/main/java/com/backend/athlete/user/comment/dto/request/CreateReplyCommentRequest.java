package com.backend.athlete.user.comment.dto.request;

import com.backend.athlete.user.comment.domain.Comment;
import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateReplyCommentRequest {

    private Long noticeId;

    @NotNull(message = "대댓글 내용을 입력 해주세요.")
    private String content;

    public CreateReplyCommentRequest(Long noticeId, String content) {
        this.noticeId = noticeId;
        this.content = content;
    }

    public static Comment toEntity(CreateReplyCommentRequest request, User user, Comment parent, Notice notice) {
        return new Comment(user, notice, parent, request.getContent());
    }
}


