package com.backend.athlete.domain.attendance.dto.response;

import lombok.Getter;

@Getter
public class GetContinuousAttendanceResponse {

    private Long userId;
    private int continuousAttendanceCount;

    public GetContinuousAttendanceResponse(Long userId, int continuousAttendanceCount) {
        this.userId = userId;
        this.continuousAttendanceCount = continuousAttendanceCount;
    }

}
