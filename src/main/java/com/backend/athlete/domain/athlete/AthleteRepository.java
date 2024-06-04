package com.backend.athlete.domain.athlete;

import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    @Query("SELECT a FROM Athlete a WHERE a.user.id = :userId AND a.dailyTime = :dailyTime")
    List<Athlete> findAthletesByUserIdAndDailyTime(@Param("userId") Long userId, @Param("dailyTime") LocalDate dailyTime);

    @Query("SELECT new com.backend.athlete.domain.athlete.data.AthleteData(a.dailyTime, a.athletics, a.type, SUM(a.record), COUNT(a.id), a.etc, a.user.name) " +
            "FROM Athlete a " +
            "WHERE a.user.id = :userId AND a.dailyTime BETWEEN :startDate AND :endDate " +
            "GROUP BY a.dailyTime, a.athletics, a.type, a.etc, a.user.name")
    List<AthleteData> findGroupedAthletesByUserIdAndYearMonth(@Param("userId") Long userId,
                                                              @Param("startDate") LocalDate startDate,
                                                              @Param("endDate") LocalDate endDate);


    @Query("SELECT a FROM Athlete a WHERE a.id = :id AND a.user.id = :userId")
    Optional<Athlete> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    List<Athlete> findByUserAndDailyTimeBetween(User findUser, LocalDate startDate, LocalDate endDate);

}
