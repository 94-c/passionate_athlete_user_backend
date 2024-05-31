package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;

    public SaveNoticeResponse(Long id, String title, String content, String imagePath) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public static SaveNoticeResponse fromEntity(Notice notice) {
        return new SaveNoticeResponse(notice.getId(), notice.getTitle(), notice.getContent(), notice.getImagePath());
    }
}