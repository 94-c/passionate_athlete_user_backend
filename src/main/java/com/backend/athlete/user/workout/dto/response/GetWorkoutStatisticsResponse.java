package com.backend.athlete.user.workout.dto.response;

import lombok.Getter;

@Getter
public class GetWorkoutStatisticsResponse {
    private long totalDuration;
    private int totalCount;
    private String averageIntensity;
    private String maxRecord;
    private double weeklySuccessRate;
    private double monthlySuccessRate;
    private double weeklyAttendanceRate;
    private double monthlyAttendanceRate;

    public GetWorkoutStatisticsResponse(long totalDuration, int totalCount, String averageIntensity, String maxRecord, double weeklySuccessRate, double monthlySuccessRate, double weeklyAttendanceRate, double monthlyAttendanceRate) {
        this.totalDuration = totalDuration;
        this.totalCount = totalCount;
        this.averageIntensity = averageIntensity;
        this.maxRecord = maxRecord;
        this.weeklySuccessRate = weeklySuccessRate;
        this.monthlySuccessRate = monthlySuccessRate;
        this.weeklyAttendanceRate = weeklyAttendanceRate;
        this.monthlyAttendanceRate = monthlyAttendanceRate;
    }
}
