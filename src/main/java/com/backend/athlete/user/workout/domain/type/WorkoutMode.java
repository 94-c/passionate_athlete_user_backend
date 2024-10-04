package com.backend.athlete.user.workout.domain.type;

public enum WorkoutMode {
    TIME_AND_ROUNDS,      // 시간과 라운드를 통과하는 방식
    ROUND_RANKING,        // 라운드 랭킹 측정
    EVERY_MINUTE_FAILURE,  // 매분 미션 실패 측정
    PASS_OR_NONPASS, // 패스 또는 실패
}
