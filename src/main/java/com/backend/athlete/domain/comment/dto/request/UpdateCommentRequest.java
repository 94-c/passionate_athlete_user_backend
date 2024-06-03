package com.backend.athlete.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCommentRequest {

    @NotNull(message = "댓글 내용을 입력 해주세요.")
    private String content;

}