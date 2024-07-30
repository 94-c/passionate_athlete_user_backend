package com.backend.athlete.domain.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduledWorkoutRepository extends JpaRepository<ScheduledWorkout, Long> {
    List<ScheduledWorkout> findByDate(LocalDate date);
}
