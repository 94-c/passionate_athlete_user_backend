package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.domain.File;
import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> imagePaths; // 다중 이미지 경로를 위한 필드
    private String userName;
    private String createdDate;

    public CreateNoticeResponse(Long id, String title, String content, List<String> imagePaths, String userName, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePaths = imagePaths;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    public static CreateNoticeResponse fromEntity(Notice notice) {
        List<String> imagePaths = notice.getFiles().stream()
                .map(File::getFilePath)
                .collect(Collectors.toList());

        return new CreateNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                imagePaths,
                notice.getUser().getName(),
                notice.getCreatedDate()
        );
    }
}
