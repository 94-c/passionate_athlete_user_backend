package com.backend.athlete.domain.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutInfoRepository extends JpaRepository<WorkoutInfo, Long> {
}
