package com.backend.athlete.domain.workoutNotice.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_record_notice_comments")
public class WorkoutRecordNoticeComment extends BaseTimeEntity {

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
    @org.hibernate.annotations.Comment("운동 공유 기록 인덱스")
    private WorkoutRecordNotice notice;

    @org.hibernate.annotations.Comment("댓글 내용")
    private String content;

    public WorkoutRecordNoticeComment(User user, WorkoutRecordNotice notice, String content) {
        this.user = user;
        this.notice = notice;
        this.content = content;
    }

    public void updateWorkoutRecordNoticeComment(String content) {
        this.content = content;
    }

}
