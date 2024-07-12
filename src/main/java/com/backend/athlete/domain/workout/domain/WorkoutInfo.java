package com.backend.athlete.domain.workout.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_info")
public class WorkoutInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("운동 정보")
    private String info;

    @ManyToOne
    @JoinColumn(name = "scheduled_workout_id", nullable = false)
    private ScheduledWorkout scheduledWorkout;

    public WorkoutInfo(String info) {
        this.info = info;
    }

    public void setScheduledWorkout(ScheduledWorkout scheduledWorkout) {
        this.scheduledWorkout = scheduledWorkout;
    }
}
