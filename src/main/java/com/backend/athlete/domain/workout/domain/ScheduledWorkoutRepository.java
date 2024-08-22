package com.backend.athlete.domain.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduledWorkoutRepository extends JpaRepository<ScheduledWorkout, Long> {
    List<ScheduledWorkout> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<ScheduledWorkout> findByScheduledDateTimeBetween(LocalDate start, LocalDate end);

    @Query("SELECT sw FROM ScheduledWorkout sw WHERE DATE(sw.scheduledDateTime) = :date")
    Optional<ScheduledWorkout> findByDate(@Param("date") LocalDate date);
}
