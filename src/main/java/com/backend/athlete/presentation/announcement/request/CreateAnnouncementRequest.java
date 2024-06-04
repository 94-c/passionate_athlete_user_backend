package com.backend.athlete.presentation.announcement.request;

import com.backend.athlete.domain.announcement.Announcement;
import com.backend.athlete.domain.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAnnouncementRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    @NotNull(message = "내용을 입력 해주세요.")
    private String content;
    private String imagePath;
    private Boolean status;

    public static Announcement toEntity(CreateAnnouncementRequest request, User user) {
        return new Announcement(
                request.getTitle(),
                request.getContent(),
                request.getImagePath(),
                request.getStatus(),
                user
        );
    }

}
