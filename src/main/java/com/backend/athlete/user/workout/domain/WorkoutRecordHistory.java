package com.backend.athlete.user.workout.domain;

import com.backend.athlete.user.exercise.domain.Exercise;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.workout.domain.type.WeightUnit;
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

    @Enumerated(EnumType.STRING)
    @Comment("무게 단위")
    private WeightUnit unit;

    @Comment("무게 값")
    private String weight;

    @Comment("라운드")
    private Integer rounds;

    @Comment("등급")
    private String rating;

    public WorkoutRecordHistory(User user, Exercise exercise, WeightUnit unit, String weight, Integer rounds, String rating) {
        this.user = user;
        this.exercise = exercise;
        this.unit = unit;
        this.weight = weight;
        this.rounds = rounds;
        this.rating = rating;
    }

    public void setWorkoutRecord(WorkoutRecord workoutRecord) {
        this.workoutRecord = workoutRecord;
    }
}
