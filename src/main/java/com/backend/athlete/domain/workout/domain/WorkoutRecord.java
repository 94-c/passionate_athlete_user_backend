package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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

    @Comment("운동 이름")
    private String exerciseName;

    @Comment("횟수")
    private Integer repetitions;

    @Comment("무게")
    private Double weight;

    @Comment("시간")
    private String duration;

    @Comment("등급")
    private String rating;

    @Comment("성공 여부")
    private Boolean success;

    public WorkoutRecord(User user, WorkoutRecordType exerciseType, ScheduledWorkout scheduledWorkout,
                         String exerciseName, Integer repetitions, Double weight, String duration,
                         String rating, Boolean success) {
        this.user = user;
        this.exerciseType = exerciseType;
        this.scheduledWorkout = scheduledWorkout;
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
    }
}
