package com.backend.athlete.user.workout.infrastructure;

import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.backend.athlete.user.workout.domain.WorkoutRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRecordRepositoryCustom {
    Page<WorkoutRecord> findMainWorkoutRecordsByDateRangeAndGenderAndRating(String startDateTime, String endDateTime, UserGenderType gender, String rating, Pageable pageable);

    List<WorkoutRecord> findByUserAndCreatedAtBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

}
