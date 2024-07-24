package com.backend.athlete.domain.workout.infrastructure;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.dto.response.DailyWorkoutCountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkoutRecordRepositoryCustom {
    Page<WorkoutRecord> findMainWorkoutRecordsByDateRangeAndGenderAndRating(String startDateTime, String endDateTime, UserGenderType gender, String rating, Pageable pageable);
    List<DailyWorkoutCountResponse> countWorkoutsByUserAndMonth(User user, int year, int month);

}
