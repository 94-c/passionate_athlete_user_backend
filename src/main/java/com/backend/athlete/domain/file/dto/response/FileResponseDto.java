package com.backend.athlete.domain.file.dto.response;

import com.backend.athlete.domain.file.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponseDto {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
    private Long noticeId;
    private String errorMessage;
    public FileResponseDto(Long id, String fileName, String filePath, String fileType, long fileSize, Long noticeId) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.noticeId = noticeId;
    }

    public FileResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static FileResponseDto fromEntity(File file) {
        Long noticeId = file.getNotice() != null ? file.getNotice().getId() : null;
        return new FileResponseDto(
                file.getId(),
                file.getFileName(),
                file.getFilePath(),
                file.getFileType(),
                file.getFileSize(),
                noticeId
        );
    }
}

