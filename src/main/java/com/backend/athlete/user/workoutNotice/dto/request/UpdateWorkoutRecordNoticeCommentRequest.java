package com.backend.athlete.user.workoutNotice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateWorkoutRecordNoticeCommentRequest {
    @NotNull(message = "댓글 내용을 입력 해주세요.")
    private String content;
}
