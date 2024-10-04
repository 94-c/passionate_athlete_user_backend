package com.backend.athlete.user.workout.domain;

import com.backend.athlete.user.workout.infrastructure.WorkoutRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom, WorkoutRecordQueryRepository {
    List<WorkoutRecord> findByUserId(Long userId);
    Optional<WorkoutRecord> findTopByUserIdOrderByDurationDesc(Long userId);
    List<WorkoutRecord> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    int countByUserId(Long userId);
}
