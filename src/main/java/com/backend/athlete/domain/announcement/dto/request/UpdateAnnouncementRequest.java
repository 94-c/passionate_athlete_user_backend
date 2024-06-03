package com.backend.athlete.domain.announcement.dto.request;

import com.backend.athlete.domain.announcement.model.Announcement;
import com.backend.athlete.domain.user.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateAnnouncementRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    @NotNull(message = "내용을 입력 해주세요.")
    private String content;
    private String imagePath;
    private Boolean status;

    public static Announcement toEntity(UpdateAnnouncementRequest request, User user) {
        return new Announcement(
                request.getTitle(),
                request.getContent(),
                request.getImagePath(),
                request.getStatus(),
                user
        );
    }
}
