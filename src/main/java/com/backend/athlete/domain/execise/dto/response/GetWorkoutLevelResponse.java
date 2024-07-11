package com.backend.athlete.domain.execise.dto.response;

import com.backend.athlete.domain.execise.domain.WorkoutLevel;
import com.backend.athlete.domain.execise.domain.type.LevelType;
import lombok.Getter;

@Getter
public class GetWorkoutLevelResponse {
    private Long id;
    private LevelType level;
    private double maleWeight;
    private double femaleWeight;
    private int maleCount;
    private int femaleCount;

    public GetWorkoutLevelResponse(Long id, LevelType level, double maleWeight, double femaleWeight, int maleCount, int femaleCount) {
        this.id = id;
        this.level = level;
        this.maleWeight = maleWeight;
        this.femaleWeight = femaleWeight;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    public static GetWorkoutLevelResponse fromEntity(WorkoutLevel level) {
        return new GetWorkoutLevelResponse(
                level.getId(),
                level.getLevel(),
                level.getMaleWeight(),
                level.getFemaleWeight(),
                level.getMaleCount(),
                level.getFemaleCount()
        );
    }
}
