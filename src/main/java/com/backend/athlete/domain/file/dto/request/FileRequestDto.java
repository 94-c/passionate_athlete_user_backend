package com.backend.athlete.domain.file.dto.request;

import com.backend.athlete.domain.file.domain.File;
import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Getter @Setter
public class FileRequestDto {
    private MultipartFile file;

    public FileRequestDto(MultipartFile file) {
        this.file = file;
    }

    public static File toEntity(FileRequestDto request, Notice notice, String uploadDir) throws IOException {
        String originalFilename = request.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(uploadDir, newFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, request.getFile().getBytes());

        return new File(originalFilename, filePath.toString(), request.getFile().getContentType(), request.getFile().getSize(), notice);
    }
}
