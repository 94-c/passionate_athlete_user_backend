package com.backend.athlete.domain.comment.model;

import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
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
    @JoinColumn(name = "notice_id", nullable = false)
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

    protected Comment() {}

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
