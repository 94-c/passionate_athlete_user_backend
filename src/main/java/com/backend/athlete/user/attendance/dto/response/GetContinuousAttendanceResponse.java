package com.backend.athlete.user.attendance.dto.response;

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
