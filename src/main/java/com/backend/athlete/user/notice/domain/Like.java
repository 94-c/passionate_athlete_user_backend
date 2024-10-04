package com.backend.athlete.user.notice.domain;

import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Like(User user, Notice notice) {
        this.user = user;
        this.notice = notice;
    }

}
