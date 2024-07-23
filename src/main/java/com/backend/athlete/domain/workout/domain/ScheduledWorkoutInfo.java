package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.exercise.domain.Exercise;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule_workout_info")
public class ScheduledWorkoutInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Comment("운동 정보")
    private String info;

    @ManyToOne
    @JoinColumn(name = "scheduled_workout_id", nullable = false)
    private ScheduledWorkout scheduledWorkout;

    public ScheduledWorkoutInfo(Exercise exercise, String info) {
        this.exercise = exercise;
        this.info = info;
    }

    public void setScheduledWorkout(ScheduledWorkout scheduledWorkout) {
        this.scheduledWorkout = scheduledWorkout;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}

