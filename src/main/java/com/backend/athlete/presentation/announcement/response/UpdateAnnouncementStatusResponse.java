package com.backend.athlete.presentation.announcement.response;

import com.backend.athlete.domain.announcement.Announcement;
import lombok.Getter;

@Getter
public class UpdateAnnouncementStatusResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private boolean status;

    public UpdateAnnouncementStatusResponse(Long id, String title, String content, String imagePath, String userName, boolean status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.status = status;
    }

    public static UpdateAnnouncementStatusResponse fromEntity(Announcement announcement) {
        return new UpdateAnnouncementStatusResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImagePath(),
                announcement.getUser().getName(),
                announcement.isStatus()
        );
    }
}
