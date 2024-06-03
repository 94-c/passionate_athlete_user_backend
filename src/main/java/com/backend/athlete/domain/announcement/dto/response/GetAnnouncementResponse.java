package com.backend.athlete.domain.announcement.dto.response;

import com.backend.athlete.domain.announcement.model.Announcement;
import lombok.Getter;

@Getter
public class GetAnnouncementResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private String createdDate;
    private String modifiedDate;

    public GetAnnouncementResponse(Long id, String title, String content, String imagePath, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
    public static GetAnnouncementResponse fromEntity(Announcement announcement) {
        return new GetAnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImagePath(),
                announcement.getUser().getName(),
                announcement.getCreatedDate(),
                announcement.getModifiedDate()
        );
    }

}
