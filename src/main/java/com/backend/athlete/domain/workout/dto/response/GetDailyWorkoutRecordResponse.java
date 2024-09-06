package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.data.WorkoutRecordCalendarData;
import com.backend.athlete.domain.workout.domain.data.WorkoutRecordData;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDailyWorkoutRecordResponse {
    private List<WorkoutRecordCalendarData> records;

    public GetDailyWorkoutRecordResponse(List<WorkoutRecordCalendarData> records) {
        this.records = records;
    }
}
