package com.backend.athlete.user.workout.domain;

import com.backend.athlete.user.user.domain.type.UserGenderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface WorkoutRecordQueryRepository {
    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.createdAt BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true AND (:rating IS NULL OR wr.rating = :rating) ORDER BY wr.rounds DESC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByRounds(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            @Param("rating") String rating,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.createdAt BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true ORDER BY wr.rounds DESC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByRoundsWithoutRating(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.createdAt BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true AND (:rating IS NULL OR wr.rating = :rating) ORDER BY wr.duration ASC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByDuration(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            @Param("rating") String rating,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.createdAt BETWEEN :startDate AND :endDate AND wr.user.gender = :gender AND wr.success = true ORDER BY wr.duration ASC")
    Page<WorkoutRecord> findSuccessfulRecordsSortedByDurationWithoutRating(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("gender") UserGenderType gender,
            Pageable pageable
    );

    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.isShared = false AND wr.user.id = :userId ORDER BY wr.createdAt DESC")
    Page<WorkoutRecord> findByUserIdAndIsSharedFalse(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM workout_record w WHERE w.user_id = :userId AND " +
            "STR_TO_DATE(w.create_date, '%Y-%m-%d %H:%i:%s') BETWEEN :startDate AND :endDate", nativeQuery = true)
    int existsByUserAndCreatedDateBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
