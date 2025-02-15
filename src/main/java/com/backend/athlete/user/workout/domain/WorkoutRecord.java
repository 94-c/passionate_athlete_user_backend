package com.backend.athlete.user.workout.domain;

import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.workout.domain.type.WorkoutRecordType;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_record")
public class WorkoutRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원")
    private User user;

    @Enumerated(EnumType.STRING)
    @Comment("운동 타입")
    private WorkoutRecordType exerciseType;

    @ManyToOne
    @JoinColumn(name = "scheduled_workout_id", nullable = true)
    @Comment("오늘의 운동")
    private ScheduledWorkout scheduledWorkout;

    @Comment("라운드")
    private Integer rounds;

    @Comment("시간")
    private String duration;

    @Comment("등급")
    private String rating;

    @Comment("성공 여부")
    private Boolean success;

    @Lob
    @Comment("운동 내용")
    @Column(name = "record_content", columnDefinition = "LONGTEXT")
    private String recordContent;

    @OneToMany(mappedBy = "workoutRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutRecordHistory> workoutHistories = new ArrayList<>();

    @Comment("공개 여부")
    @Column(name = "is_shared")
    private Boolean isShared;

    public WorkoutRecord(User user, WorkoutRecordType exerciseType, ScheduledWorkout scheduledWorkout,
                         Integer rounds, String duration,
                         String rating, Boolean success, String recordContent, Boolean isShared) {
        this.user = user;
        this.exerciseType = exerciseType;
        this.scheduledWorkout = scheduledWorkout;
        this.rounds = rounds;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.recordContent = recordContent;
        this.isShared = isShared;
    }

    public void addWorkoutHistory(WorkoutRecordHistory history) {
        workoutHistories.add(history);
        history.setWorkoutRecord(this);
    }

    public String getScheduledWorkoutTitle() {
        return scheduledWorkout != null ? scheduledWorkout.getTitle() : null;
    }

    public void setIsShared() {
        this.isShared = true;
    }

}

