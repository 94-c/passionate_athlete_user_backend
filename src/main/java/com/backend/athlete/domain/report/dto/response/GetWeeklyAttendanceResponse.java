package com.backend.athlete.domain.report.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetWeeklyAttendanceResponse {
    private int totalDaysPresent;
    private int totalDaysSuccess;
    private int totalDaysFail;
    private List<LocalDate> presentDays;
    private List<LocalDate> successDays;
    private List<LocalDate> failDays;

    public GetWeeklyAttendanceResponse(int totalDaysPresent, int totalDaysSuccess, int totalDaysFail,
                                       List<LocalDate> presentDays, List<LocalDate> successDays, List<LocalDate> failDays) {
        this.totalDaysPresent = totalDaysPresent;
        this.totalDaysSuccess = totalDaysSuccess;
        this.totalDaysFail = totalDaysFail;
        this.presentDays = presentDays;
        this.successDays = successDays;
        this.failDays = failDays;
    }

    public static GetWeeklyAttendanceResponse fromEntity(int totalDaysPresent, int totalDaysSuccess, int totalDaysFail,
                                                                          List<LocalDate> presentDays, List<LocalDate> successDays, List<LocalDate> failDays) {
        return new GetWeeklyAttendanceResponse(totalDaysPresent, totalDaysSuccess, totalDaysFail, presentDays, successDays, failDays);
    }
}
