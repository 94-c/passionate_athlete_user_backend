package com.backend.athlete.domain.workout.dto.response;

import lombok.Getter;

@Getter
public class DailyWorkoutCountResponse {

    private String date;
    private Long workoutCount;

    public DailyWorkoutCountResponse(String date, Long workoutCount) {
        this.date = date;
        this.workoutCount = workoutCount;
    }
}
