package com.backend.athlete.domain.announcement.dto.response;

import com.backend.athlete.domain.announcement.model.Announcement;
import lombok.Getter;

@Getter
public class UpdateAnnouncementResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private String modifiedDate;

    public UpdateAnnouncementResponse(Long id, String title, String content, String imagePath, String userName, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.modifiedDate = modifiedDate;
    }

    public static UpdateAnnouncementResponse fromEntity(Announcement announcement) {
        return new UpdateAnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImagePath(),
                announcement.getUser().getName(),
                announcement.getModifiedDate()
        );
    }
}
