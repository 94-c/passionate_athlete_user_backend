package com.backend.athlete.user.file.application;

import com.backend.athlete.user.file.domain.File;
import com.backend.athlete.user.file.domain.FileRepository;
import com.backend.athlete.user.file.dto.request.FileRequestDto;
import com.backend.athlete.user.file.dto.response.FileResponseDto;
import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.notice.domain.NoticeRepository;
import com.backend.athlete.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final NoticeRepository noticeRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileResponseDto uploadFile(MultipartFile file, Long noticeId) throws IOException {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new NotFoundException("Notice not found", HttpStatus.NOT_FOUND));

        FileRequestDto fileRequest = new FileRequestDto(file);
        File fileEntity = FileRequestDto.toEntity(fileRequest, notice, uploadDir);
        File savedFile = fileRepository.save(fileEntity);

        return FileResponseDto.fromEntity(savedFile);
    }
}
