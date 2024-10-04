package com.backend.athlete.user.workout.dto.response;

import lombok.Getter;

@Getter
public class MonthlyWorkoutRecordResponse {

    private String date;
    private Long workoutCount;

    public MonthlyWorkoutRecordResponse(String date, Long workoutCount) {
        this.date = date;
        this.workoutCount = workoutCount;
    }
}

