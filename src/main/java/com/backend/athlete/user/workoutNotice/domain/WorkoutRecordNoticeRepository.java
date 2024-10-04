package com.backend.athlete.user.workoutNotice.domain;

import com.backend.athlete.user.workout.domain.WorkoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRecordNoticeRepository extends JpaRepository<WorkoutRecordNotice, Long> {
    Optional<WorkoutRecordNotice> findByWorkoutRecord(WorkoutRecord workoutRecord);
}
