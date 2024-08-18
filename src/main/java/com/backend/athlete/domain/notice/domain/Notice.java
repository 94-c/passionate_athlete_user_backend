package com.backend.athlete.domain.notice.domain;

import com.backend.athlete.domain.file.domain.File;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notices")
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시판 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Comment("게시판 제목")
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @Comment("게시판 내용")
    private String content;

    @ManyToOne
    @JoinColumn(name = "kind_id", nullable = false)
    @Comment("게시판 종류")
    private NoticeType kind;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @Comment("게시글 활성화")
    private boolean status;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.backend.athlete.domain.comment.domain.Comment> comments = new ArrayList<>();


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Notice(String title, String content, NoticeType kind, boolean status, User user) {
        this.title = title;
        this.content = content;
        this.kind = kind;
        this.status = status;
        this.user = user;
    }

    public void updateNotice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addFile(File file) {
        files.add(file);
        file.setNotice(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setNotice(null);
    }

    public void deletedNotice(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}


