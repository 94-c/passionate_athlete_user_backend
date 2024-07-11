package com.backend.athlete.domain.notice.domain;

import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "files")
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("파일 인덱스")
    private Long id;

    @Comment("파일 이름")
    private String fileName;

    @Comment("파일 경로")
    private String filePath;

    @Comment("파일 타입")
    private String fileType;

    @Comment("파일 크기")
    private long fileSize;

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    public File(String fileName, String filePath, String fileType, long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
