package com.backend.athlete.domain.execise.domain;

import com.backend.athlete.domain.execise.dto.request.CreateWorkoutLevelRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "workout_info")
public class WorkoutInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wod_id")
    @Comment("와드")
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "workoutInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutLevel> levels;
    private Time time;

    public WorkoutInfo() {
        this.levels = new ArrayList<>();
    }

    public WorkoutInfo(Workout workout, Exercise exercise, List<CreateWorkoutLevelRequest> levelRequests) {
        this.workout = workout;
        this.exercise = exercise;
        if (levelRequests != null) {
            this.levels = levelRequests.stream()
                    .map(levelRequest -> CreateWorkoutLevelRequest.toEntity(levelRequest, this))
                    .collect(Collectors.toList());
        }
    }
    public WorkoutInfo(Workout workout, Exercise exercise) {
        this.workout = workout;
        this.exercise = exercise;
        this.levels = new ArrayList<>();
    }

    public void addLevel(WorkoutLevel level) {
        level.setWorkoutInfo(this);
        levels.add(level);
    }

    public void setLevels(List<WorkoutLevel> levels) {
        this.levels.clear();
        levels.forEach(this::addLevel);
    }


}
