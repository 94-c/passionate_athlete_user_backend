package com.backend.athlete.user.comment.domain;

import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("댓글 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @org.hibernate.annotations.Comment("회원 아이디")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    @org.hibernate.annotations.Comment("게시글 인덱스")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Comment("대댓글")
    private Comment parent;

    @org.hibernate.annotations.Comment("댓글 내용")
    private String content;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Comment("대댓글 목록")
    private List<Comment> replies = new ArrayList<>();

    //댓글 작성
    public Comment(User user, Notice notice, String content) {
        this.user = user;
        this.notice = notice;
        this.content = content;
    }

    //대댓글 작성
    public Comment(User user, Notice notice, Comment parent, String content) {
        this.user = user;
        this.notice = notice;
        this.parent = parent;
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}
