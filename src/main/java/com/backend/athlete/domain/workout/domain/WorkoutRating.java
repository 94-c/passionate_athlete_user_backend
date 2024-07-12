package com.backend.athlete.domain.workout.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_rating")
public class WorkoutRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("성별")
    private String gender;

    @Comment("등급")
    private String rating;

    @Comment("기준")
    private String criteria;

    @ManyToOne
    @JoinColumn(name = "scheduled_workout_id", nullable = false)
    private ScheduledWorkout scheduledWorkout;

    public WorkoutRating(String gender, String rating, String criteria) {
        this.gender = gender;
        this.rating = rating;
        this.criteria = criteria;
    }

    public void setScheduledWorkout(ScheduledWorkout scheduledWorkout) {
        this.scheduledWorkout = scheduledWorkout;
    }
}
