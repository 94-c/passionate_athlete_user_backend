package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.workout.domain.type.WorkoutType;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
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

    @Comment("라운드")
    private int rounds;

    @Comment("시간")
    private String time;

    @Comment("날짜")
    @Column(unique = true)
    private LocalDate date;

    @Comment("비고")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Comment("운동 형태")
    private WorkoutType workoutType;

    @OneToMany(mappedBy = "scheduledWorkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutInfo> workoutInfos = new ArrayList<>();

    @OneToMany(mappedBy = "scheduledWorkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutRating> workoutRatings = new ArrayList<>();

    public ScheduledWorkout(String title, LocalDate date, int rounds, String time, String notes, WorkoutType workoutType) {
        this.title = title;
        this.date = date;
        this.rounds = rounds;
        this.time = time;
        this.notes = notes;
        this.workoutType = workoutType;
    }

    public void addWorkoutInfo(WorkoutInfo workoutInfo) {
        workoutInfos.add(workoutInfo);
        workoutInfo.setScheduledWorkout(this);
    }

    public void addWorkoutRating(WorkoutRating workoutRating) {
        workoutRatings.add(workoutRating);
        workoutRating.setScheduledWorkout(this);
    }

    public void setWorkoutInfos(List<WorkoutInfo> workoutInfos) {
        this.workoutInfos = workoutInfos;
        workoutInfos.forEach(info -> info.setScheduledWorkout(this));
    }

    public void setWorkoutRatings(List<WorkoutRating> workoutRatings) {
        this.workoutRatings = workoutRatings;
        workoutRatings.forEach(rating -> rating.setScheduledWorkout(this));
    }
}

