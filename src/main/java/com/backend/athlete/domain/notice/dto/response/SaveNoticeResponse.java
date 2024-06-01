package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;

    public SaveNoticeResponse(Long id, String title, String content, String imagePath, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
    }

    public static SaveNoticeResponse fromEntity(Notice notice) {
        User findUser = notice.getUser();
        return new SaveNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                findUser.getName()
        );
    }
}