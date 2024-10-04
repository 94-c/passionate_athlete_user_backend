package com.backend.athlete.user.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledWorkoutInfoRepository extends JpaRepository<ScheduledWorkoutInfo, Long> {
}
