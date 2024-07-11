package com.backend.athlete.domain.execise.infrastructure;

import com.backend.athlete.domain.execise.QWorkout;
import com.backend.athlete.domain.execise.QWorkoutInfo;
import com.backend.athlete.domain.execise.QWorkoutLevel;
import com.backend.athlete.domain.execise.domain.Workout;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Workout findWorkoutWithDetailsById(Long id) {
        return factory.selectFrom(QWorkout.workout)
                .leftJoin(QWorkout.workout.workoutInfos, QWorkoutInfo.workoutInfo).fetchJoin()
                .leftJoin(QWorkoutInfo.workoutInfo.levels, QWorkoutLevel.workoutLevel).fetchJoin()
                .where(QWorkout.workout.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<Workout> findAllWithDetails(String title, Pageable pageable) {
        List<Workout> content = factory.selectFrom(QWorkout.workout)
                .leftJoin(QWorkout.workout.workoutInfos, QWorkoutInfo.workoutInfo).fetchJoin()
                .leftJoin(QWorkoutInfo.workoutInfo.levels, QWorkoutLevel.workoutLevel).fetchJoin()
                .where(QWorkout.workout.title.containsIgnoreCase(title))  // ensure title filter is applied here
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable,
                () -> factory.selectFrom(QWorkout.workout)
                        .where(QWorkout.workout.title.containsIgnoreCase(title))
                        .fetchCount());
    }

}
