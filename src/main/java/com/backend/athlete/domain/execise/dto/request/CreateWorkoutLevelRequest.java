package com.backend.athlete.domain.execise.dto.request;

import com.backend.athlete.domain.execise.domain.WorkoutInfo;
import com.backend.athlete.domain.execise.domain.WorkoutLevel;
import com.backend.athlete.domain.execise.domain.type.LevelType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateWorkoutLevelRequest {
    private String level;
    private int maleWeight;
    private int femaleWeight;
    private int maleCount;
    private int femaleCount;

    public CreateWorkoutLevelRequest(String level, int maleWeight, int femaleWeight, int maleCount, int femaleCount) {
        this.level = level;
        this.maleWeight = maleWeight;
        this.femaleWeight = femaleWeight;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    public static WorkoutLevel toEntity(CreateWorkoutLevelRequest request, WorkoutInfo workoutInfo) {
        return new WorkoutLevel(
                LevelType.valueOf(request.getLevel()),
                workoutInfo,
                request.getMaleWeight(),
                request.getFemaleWeight(),
                request.getMaleCount(),
                request.getFemaleCount()
        );
    }
}
