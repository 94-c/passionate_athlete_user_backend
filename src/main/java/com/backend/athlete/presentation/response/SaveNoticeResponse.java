package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.notice.Notice;
import lombok.Getter;

@Getter
public class SaveNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private String createdDate;

    public SaveNoticeResponse(Long id, String title, String content, String imagePath, String userName, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    public static SaveNoticeResponse fromEntity(Notice notice) {
        return new SaveNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                notice.getCreatedDate()
        );
    }
}