package com.backend.athlete.domain.athlete.infrastructure;

import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AthleteRepositoryCustom {
    List<Athlete> findAthletesByUserIdAndDailyTime(Long userId, LocalDate dailyTime);
    List<AthleteData> findGroupedAthletesByUserIdAndYearMonth(Long userId, LocalDate startDate, LocalDate endDate);
    Optional<Athlete> findByIdAndUserId(Long id, Long userId);
    List<Athlete> findByUserAndDailyTimeBetween(User findUser, LocalDate startDate, LocalDate endDate);

}
