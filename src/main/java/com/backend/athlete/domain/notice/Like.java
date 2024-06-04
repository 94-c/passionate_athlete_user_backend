package com.backend.athlete.domain.notice;

import com.backend.athlete.domain.user.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
@Getter
@Entity
@Table(name = "likes")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("좋아요 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    @Comment("게시판 인덱스")
    private Notice notice;

    protected Like() {}

    public Like(User user, Notice notice) {
        this.user = user;
        this.notice = notice;
    }

}