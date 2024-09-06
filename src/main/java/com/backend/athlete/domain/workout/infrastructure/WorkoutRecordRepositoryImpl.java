package com.backend.athlete.domain.workout.infrastructure;

import com.backend.athlete.domain.user.domain.QUser;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.domain.QWorkoutRecord;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutRecordRepositoryImpl implements WorkoutRecordRepositoryCustom {
    private final JPAQueryFactory factory;

    private BooleanBuilder toBooleanBuilder(String startDateTime, String endDateTime, UserGenderType gender, String rating) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QWorkoutRecord workoutRecord = QWorkoutRecord.workoutRecord;
        QUser user = QUser.user;

        if (startDateTime != null) {
            booleanBuilder.and(workoutRecord.createdDate.goe(startDateTime));
        }
        if (endDateTime != null) {
            booleanBuilder.and(workoutRecord.createdDate.loe(endDateTime));
        }
        if (gender != null) {
            booleanBuilder.and(user.gender.eq(gender));
        }
        if (rating != null && !rating.isEmpty()) {
            booleanBuilder.and(workoutRecord.rating.eq(rating));
        }

        booleanBuilder.and(workoutRecord.exerciseType.eq(WorkoutRecordType.MAIN));
        booleanBuilder.and(workoutRecord.success.isTrue());

        return booleanBuilder;
    }

    @Override
    public Page<WorkoutRecord> findMainWorkoutRecordsByDateRangeAndGenderAndRating(
            String startDateTime,
            String endDateTime,
            UserGenderType gender,
            String rating,
            Pageable pageable) {

        QWorkoutRecord workoutRecord = QWorkoutRecord.workoutRecord;
        QUser user = QUser.user;

        BooleanBuilder booleanBuilder = toBooleanBuilder(startDateTime, endDateTime, gender, rating);

        QueryResults<WorkoutRecord> results = factory.selectFrom(workoutRecord)
                .join(workoutRecord.user, user)
                .where(booleanBuilder)
                .orderBy(
                        Expressions.stringTemplate("FUNCTION('STR_TO_DATE', {0}, '%i:%s')", workoutRecord.duration).asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public List<WorkoutRecord> findByUserAndCreatedAtBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        QWorkoutRecord workoutRecord = QWorkoutRecord.workoutRecord;

        return factory.selectFrom(workoutRecord)
                .where(workoutRecord.user.eq(user)
                        .and(workoutRecord.createdAt.between(
                                startDate.withHour(15).withMinute(0).withSecond(0),
                                endDate.withHour(14).withMinute(59).withSecond(59).withNano(999999999))))
                .fetch();
    }
}


