package com.backend.athlete.domain.notice;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.List;

@Getter
@Entity
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

    @Comment("게시판 내용")
    private String content;

    @ManyToOne
    @JoinColumn(name = "kind_id", nullable = false)
    @Comment("게시판 종류")
    private NoticeType kind;

    @Comment("이미지 파일 경로")
    private String imagePath;

    @Comment("게시글 활성화")
    private boolean status;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.backend.athlete.domain.comment.Comment> comments;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    public Notice() {}

    public Notice(String title, String content, NoticeType kind, String imagePath, boolean status, User user) {
        this.title = title;
        this.content = content;
        this.kind = kind;
        this.imagePath = imagePath;
        this.status = status;
        this.user = user;
    }

    public void updateNotice(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

