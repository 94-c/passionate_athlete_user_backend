package com.backend.athlete.domain.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduledWorkoutRepository extends JpaRepository<ScheduledWorkout, Long>, ScheduledWorkoutQueryRepository{
    List<ScheduledWorkout> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
