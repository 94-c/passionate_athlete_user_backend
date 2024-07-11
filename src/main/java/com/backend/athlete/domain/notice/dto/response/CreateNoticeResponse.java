package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;

@Getter
public class CreateNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private String createdDate;

    public CreateNoticeResponse(Long id, String title, String content, String imagePath, String userName, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    public static CreateNoticeResponse fromEntity(Notice notice) {
        return new CreateNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                notice.getCreatedDate()
        );
    }
}
