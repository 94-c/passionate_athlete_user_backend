package com.backend.athlete.user.workout.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduledWorkoutQueryRepository {
    @Query("SELECT sw FROM ScheduledWorkout sw WHERE DATE(sw.scheduledDateTime) = :date")
    Optional<ScheduledWorkout> findByDate(@Param("date") LocalDate date);
}
