package com.backend.athlete.domain.notice.dto.request;

import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.user.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveNoticeRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    private String content;
    private String imagePath;

    protected SaveNoticeRequest() {}

    public SaveNoticeRequest(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public static Notice toEntity(SaveNoticeRequest dto, User user) {
        return new Notice(dto.getTitle(), dto.getContent(), dto.getImagePath(), user);
    }
}
