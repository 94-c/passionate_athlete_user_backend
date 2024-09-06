package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.infrastructure.WorkoutRecordRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom, WorkoutRecordQueryRepository {
    List<WorkoutRecord> findByUserId(Long userId);
    Optional<WorkoutRecord> findTopByUserIdOrderByDurationDesc(Long userId);
    List<WorkoutRecord> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    int countByUserId(Long userId);

}
