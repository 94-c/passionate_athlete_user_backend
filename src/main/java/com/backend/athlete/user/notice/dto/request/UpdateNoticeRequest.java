package com.backend.athlete.user.notice.dto.request;

import com.backend.athlete.user.file.domain.File;
import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.notice.domain.NoticeType;
import com.backend.athlete.user.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdateNoticeRequest {
    @NotNull(message = "제목을 입력 해주세요.")
    private String title;
    private String content;
    private Long kindId;
    private boolean status;

    public static Notice toEntity(UpdateNoticeRequest request, User user, NoticeType kind, List<File> files) {
        Notice notice = new Notice(request.getTitle(), request.getContent(), kind, request.isStatus(), user);
        for (File file : files) {
            notice.addFile(file);
        }
        return notice;
    }
}
