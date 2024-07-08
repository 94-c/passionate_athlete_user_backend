package com.backend.athlete.domain.athlete.infrastructure;

import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.athlete.QAthlete;
import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AthleteRepositoryImpl implements AthleteRepositoryCustom{

    private final JPAQueryFactory factory;

    @Override
    public List<Athlete> findAthletesByUserIdAndDailyTime(Long userId, LocalDate dailyTime) {
        return factory.selectFrom(QAthlete.athlete)
                .where(QAthlete.athlete.user.id.eq(userId)
                        .and(QAthlete.athlete.dailyTime.eq(dailyTime)))
                .fetch();
    }

    @Override
    public List<AthleteData> findGroupedAthletesByUserIdAndYearMonth(Long userId, LocalDate startDate, LocalDate endDate) {
        return factory.select(Projections.constructor(AthleteData.class,
                        QAthlete.athlete.dailyTime,
                        QAthlete.athlete.athletics,
                        QAthlete.athlete.type,
                        QAthlete.athlete.round.sum(), // Integer 타입 합계
                        QAthlete.athlete.id.count().as("count"),
                        QAthlete.athlete.etc,
                        QAthlete.athlete.user.name.as("username")))
                .from(QAthlete.athlete)
                .where(QAthlete.athlete.user.id.eq(userId)
                        .and(QAthlete.athlete.dailyTime.between(startDate, endDate)))
                .groupBy(QAthlete.athlete.dailyTime, QAthlete.athlete.athletics, QAthlete.athlete.type, QAthlete.athlete.etc, QAthlete.athlete.user.name)
                .fetch();
    }

    @Override
    public Optional<Athlete> findByIdAndUserId(Long id, Long userId) {
        Athlete athlete = factory.selectFrom(QAthlete.athlete)
                .where(QAthlete.athlete.id.eq(id)
                        .and(QAthlete.athlete.user.id.eq(userId)))
                .fetchOne();

        return Optional.ofNullable(athlete);
    }

    @Override
    public List<Athlete> findByUserAndDailyTimeBetween(User user, LocalDate startDate, LocalDate endDate) {
        return factory.selectFrom(QAthlete.athlete)
                .where(QAthlete.athlete.user.eq(user)
                        .and(QAthlete.athlete.dailyTime.between(startDate, endDate)))
                .fetch();
    }

}
