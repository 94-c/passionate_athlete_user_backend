package com.backend.athlete.domain.execise.domain;

import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "workout")
public class Workout extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("운동명")
    private String title;

    @Comment("운동 설명")
    private String description;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("세부 운동 항목")
    private Set<WorkoutInfo> workoutInfos;

    @Comment("운동 라운드")
    private String round;

    @Comment("운동 시간")
    private Time time;
    protected Workout() {}

    public Workout(String title, String description, String round, Time time) {
        this.title = title;
        this.description = description;
        this.round = round;
        this.time = time;
        this.workoutInfos = new HashSet<>();
    }

    public void addWorkoutInfo(WorkoutInfo workoutInfo) {
        workoutInfos.add(workoutInfo);
    }

    public void update(String title, String description, String round, Time time) {
        this.title = title;
        this.description = description;
        this.round = round;
        this.time = time;
        this.workoutInfos = new HashSet<>();
    }

    public void updateWorkoutInfos(List<WorkoutInfo> newWorkoutInfos) {
        this.workoutInfos.clear();
        this.workoutInfos.addAll(newWorkoutInfos);
    }

}
