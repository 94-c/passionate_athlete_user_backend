package com.backend.athlete.user.notice.dto.request;

import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.notice.domain.NoticeType;
import com.backend.athlete.user.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateNoticeRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    private String content;
    private Long kindId;
    private boolean status;

    public static Notice toEntity(CreateNoticeRequest request, User user, NoticeType kind) {
        return new Notice(request.getTitle(), request.getContent(), kind, request.isStatus(), user);
    }
}
