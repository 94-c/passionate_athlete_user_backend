package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.user.domain.type.UserGenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long> {
    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.exerciseType = 'MAIN' AND wr.success = true AND wr.createdDate BETWEEN :startDate AND :endDate ORDER BY wr.rating DESC, FUNCTION('STR_TO_DATE', wr.duration, '%i:%s') ASC")
    List<WorkoutRecord> findMainWorkoutRecordsByDateRangeAndGender(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("gender") UserGenderType gender);

}
