package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.announcement.Announcement;
import lombok.Getter;

@Getter
public class PageSearchAnnouncementResponse {
    private Long id;
    private String userName;
    private String title;
    private String content;
    private String imagePath;

    public PageSearchAnnouncementResponse(Long id, String userName, String title, String content, String imagePath) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public static PageSearchAnnouncementResponse fromEntity(Announcement announcement) {
        return new PageSearchAnnouncementResponse(
                announcement.getId(),
                announcement.getUser().getName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImagePath()
        );
    }
}
