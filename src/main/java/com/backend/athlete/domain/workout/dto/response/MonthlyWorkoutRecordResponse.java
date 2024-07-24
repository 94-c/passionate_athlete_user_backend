package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MonthlyWorkoutRecordResponse {

    private String date;
    private Long workoutCount;

    public MonthlyWorkoutRecordResponse(String date, Long workoutCount) {
        this.date = date;
        this.workoutCount = workoutCount;
    }
}

