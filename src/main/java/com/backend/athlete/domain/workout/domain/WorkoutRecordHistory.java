package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_record_history")
public class WorkoutRecordHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_record_id", nullable = false)
    @Comment("운동 기록")
    private WorkoutRecord workoutRecord;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    @Comment("운동")
    private Exercise exercise;

    @Comment("무게")
    private String weight;

    @Comment("반복 횟수")
    private Integer repetitions;

    @Comment("등급")
    private String rating;

    public WorkoutRecordHistory(User user, Exercise exercise, String weight, Integer repetitions, String rating) {
        this.user = user;
        this.exercise = exercise;
        this.weight = weight;
        this.repetitions = repetitions;
        this.rating = rating;
    }

    public void setWorkoutRecord(WorkoutRecord workoutRecord) {
        this.workoutRecord = workoutRecord;
    }
}
