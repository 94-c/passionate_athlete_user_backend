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

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom {
    List<WorkoutRecord> findByUserId(Long userId);
    Optional<WorkoutRecord> findTopByUserIdOrderByDurationDesc(Long userId);
    List<WorkoutRecord> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    int countByUserId(Long userId);

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.scheduledWorkout.scheduledDateTime BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true AND (:rating IS NULL OR wr.rating = :rating) ORDER BY wr.rounds DESC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByRounds(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            @Param("rating") String rating,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.scheduledWorkout.scheduledDateTime BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true ORDER BY wr.rounds DESC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByRoundsWithoutRating(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.scheduledWorkout.scheduledDateTime BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true AND (:rating IS NULL OR wr.rating = :rating) ORDER BY wr.duration ASC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByDuration(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            @Param("rating") String rating,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.scheduledWorkout.scheduledDateTime BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true ORDER BY wr.duration ASC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByDurationWithoutRating(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            Pageable pageable
    );
}
