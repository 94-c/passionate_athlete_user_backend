package com.backend.athlete.user.workout.dto.response;

import com.backend.athlete.user.workout.domain.data.WorkoutRecordCalendarData;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDailyWorkoutRecordResponse {
    private List<WorkoutRecordCalendarData> records;

    public GetDailyWorkoutRecordResponse(List<WorkoutRecordCalendarData> records) {
        this.records = records;
    }
}
