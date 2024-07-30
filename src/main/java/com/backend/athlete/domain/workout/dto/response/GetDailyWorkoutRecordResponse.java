package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.data.WorkoutRecordData;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDailyWorkoutRecordResponse {
    private List<WorkoutRecordData> records;

    public GetDailyWorkoutRecordResponse(List<WorkoutRecordData> records) {
        this.records = records;
    }
}
