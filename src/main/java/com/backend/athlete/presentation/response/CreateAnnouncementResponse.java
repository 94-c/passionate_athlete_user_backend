package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.announcement.Announcement;
import lombok.Getter;

@Getter
public class CreateAnnouncementResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private String createdDate;

    public CreateAnnouncementResponse(Long id, String title, String content, String imagePath, String userName, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    public static CreateAnnouncementResponse fromEntity(Announcement announcement) {
        return new CreateAnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImagePath(),
                announcement.getUser().getName(),
                announcement.getCreatedDate()
        );
    }
}
