package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.infrastructure.WorkoutRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom {
    @Query("SELECT w FROM WorkoutRecord w WHERE w.user = :user AND YEAR(w.createdDate) = :year AND MONTH(w.createdDate) = :month")
    List<WorkoutRecord> findAllByUserAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);
}
