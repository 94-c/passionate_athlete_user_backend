package com.backend.athlete.user.workout.domain;

import com.backend.athlete.user.workout.domain.type.WorkoutMode;
import com.backend.athlete.user.workout.domain.type.WorkoutType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scheduled_workout")
public class ScheduledWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("제목")
    private String title;

    @Comment("운동 날짜 및 시작 시간")
    private LocalDateTime scheduledDateTime;

    @Comment("총 운동 시간")
    private String time;

    @Comment("비고")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Comment("운동 형태")
    private WorkoutType workoutType;

    @Enumerated(EnumType.STRING)
    @Comment("운동 방식")
    private WorkoutMode workoutMode;

    @OneToMany(mappedBy = "scheduledWorkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduledWorkoutInfo> scheduledWorkoutInfos = new ArrayList<>();

    @OneToMany(mappedBy = "scheduledWorkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduledWorkoutRating> scheduledWorkoutRatings = new ArrayList<>();

    @Comment("라운드")
    private Integer round;
    public ScheduledWorkout(String title, LocalDateTime scheduledDateTime, String time, String notes, WorkoutType workoutType, WorkoutMode workoutMode, Integer round) {
        this.title = title;
        this.scheduledDateTime = scheduledDateTime;
        this.time = time;
        this.notes = notes;
        this.workoutType = workoutType;
        this.workoutMode = workoutMode;
        this.round = round;
    }

    public void addWorkoutInfo(ScheduledWorkoutInfo scheduledWorkoutInfo) {
        scheduledWorkoutInfos.add(scheduledWorkoutInfo);
        scheduledWorkoutInfo.setScheduledWorkout(this);
    }

    public void addWorkoutRating(ScheduledWorkoutRating scheduledWorkoutRating) {
        scheduledWorkoutRatings.add(scheduledWorkoutRating);
        scheduledWorkoutRating.setScheduledWorkout(this);
    }

    public void setScheduledWorkoutInfos(List<ScheduledWorkoutInfo> scheduledWorkoutInfos) {
        this.scheduledWorkoutInfos = scheduledWorkoutInfos;
        scheduledWorkoutInfos.forEach(info -> info.setScheduledWorkout(this));
    }

    public void setScheduledWorkoutRatings(List<ScheduledWorkoutRating> scheduledWorkoutRatings) {
        this.scheduledWorkoutRatings = scheduledWorkoutRatings;
        scheduledWorkoutRatings.forEach(rating -> rating.setScheduledWorkout(this));
    }
}

