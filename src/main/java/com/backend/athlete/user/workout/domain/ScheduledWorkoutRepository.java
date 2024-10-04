package com.backend.athlete.user.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledWorkoutRepository extends JpaRepository<ScheduledWorkout, Long>, ScheduledWorkoutQueryRepository{
    List<ScheduledWorkout> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
