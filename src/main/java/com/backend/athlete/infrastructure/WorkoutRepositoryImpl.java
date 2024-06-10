package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.execise.QWorkout;
import com.backend.athlete.domain.execise.QWorkoutInfo;
import com.backend.athlete.domain.execise.QWorkoutLevel;
import com.backend.athlete.domain.execise.Workout;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutRepositoryCustom{

    private final JPAQueryFactory factory;

    @Override
    public Workout findWorkoutWithDetailsById(Long id) {
        return factory.selectFrom(QWorkout.workout)
                .leftJoin(QWorkout.workout.workoutInfos, QWorkoutInfo.workoutInfo).fetchJoin()
                .leftJoin(QWorkoutInfo.workoutInfo.levels, QWorkoutLevel.workoutLevel).fetchJoin()
                .where(QWorkout.workout.id.eq(id))
                .fetchOne();
    }

}
