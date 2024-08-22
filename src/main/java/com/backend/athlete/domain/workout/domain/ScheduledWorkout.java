package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.workout.domain.type.WorkoutMode;
import com.backend.athlete.domain.workout.domain.type.WorkoutType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
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
    private LocalDateTime date;  // 운동 날짜와 시작 시간

    @Comment("총 운동 시간")
    private String time;  // 총 운동 시간 (예: "28:00")

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

    public ScheduledWorkout(String title, LocalDateTime date, String time, String notes, WorkoutType workoutType, WorkoutMode workoutMode) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.workoutType = workoutType;
        this.workoutMode = workoutMode;
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

