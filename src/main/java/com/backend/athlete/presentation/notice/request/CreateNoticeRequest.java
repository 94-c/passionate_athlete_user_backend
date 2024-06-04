package com.backend.athlete.presentation.notice.request;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateNoticeRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    private String content;
    private String imagePath;
    public static Notice toEntity(CreateNoticeRequest request, User user) {
        return new Notice(request.getTitle(), request.getContent(), request.getImagePath(), user);
    }
}
