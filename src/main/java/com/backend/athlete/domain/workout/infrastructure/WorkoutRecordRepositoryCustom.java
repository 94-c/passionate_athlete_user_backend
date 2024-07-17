package com.backend.athlete.domain.workout.infrastructure;

import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRecordRepositoryCustom {
    List<WorkoutRecord> findMainWorkoutRecordsByDateRangeAndGender(String startDateTime, String endDateTime, UserGenderType gender, int limit);
}
