package com.backend.athlete.domain.athlete.repository;

import com.backend.athlete.domain.athlete.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    @Query("SELECT a FROM Athlete a WHERE a.user.id = :userId AND a.dailyTime = :dailyTime")
    List<Athlete> findAthletesByUserIdAndDailyTime(@Param("userId") Long userId, @Param("dailyTime") LocalDate dailyTime);

}
