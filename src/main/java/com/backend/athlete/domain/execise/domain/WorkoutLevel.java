package com.backend.athlete.domain.execise.domain;

import com.backend.athlete.domain.execise.domain.type.LevelType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;


@Getter
@Entity
@Table(name = "workout_level")
public class WorkoutLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("운동 레벨")
    @Enumerated(EnumType.STRING)
    private LevelType level;

    @ManyToOne
    @JoinColumn(name = "workout_info_id")
    private WorkoutInfo workoutInfo;

    @Comment("남자 무게")
    private int maleWeight;

    @Comment("여자 무게")
    private int femaleWeight;

    @Comment("남자 카운트")
    private int maleCount;

    @Comment("여자 카운트")
    private int femaleCount;
    protected WorkoutLevel() {}

    public WorkoutLevel(LevelType level, WorkoutInfo workoutInfo, int maleWeight, int femaleWeight, int maleCount, int femaleCount) {
        this.level = level;
        this.workoutInfo = workoutInfo;
        this.maleWeight = maleWeight;
        this.femaleWeight = femaleWeight;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    public void setWorkoutInfo(WorkoutInfo workoutInfo) {
        this.workoutInfo = workoutInfo;
    }

}
