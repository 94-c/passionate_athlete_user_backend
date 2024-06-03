package com.backend.athlete.domain.announcement.model;

import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "announcements")
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("공지사항 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Comment("공지사항 제목")
    private String title;

    @Comment("공지사항 내용")
    private String content;

    @Comment("이미지 파일 경로")
    private String imagePath;

    @Comment("활성화")
    private boolean status;

    protected Announcement() {}

    public Announcement(String title, String content, String imagePath, boolean status, User user) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.status = status;
        this.user = user;
    }

    public void update(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public void isStatus(boolean status) {
        this.status = status;
    }

}
