package com.backend.athlete.domain.attendance.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetMonthlyAttendanceResponse {

    private int totalDaysPresent;
    private List<LocalDate> presentDays;
    private int totalDaysInMonth;

    public GetMonthlyAttendanceResponse(int totalDaysPresent, List<LocalDate> presentDays, int totalDaysInMonth) {
        this.totalDaysPresent = totalDaysPresent;
        this.presentDays = presentDays;
        this.totalDaysInMonth = totalDaysInMonth;
    }

    // Entity -> Dto 변환
    public static GetMonthlyAttendanceResponse fromEntity(int totalDaysPresent, List<LocalDate> presentDays, int totalDaysInMonth) {
        return new GetMonthlyAttendanceResponse(totalDaysPresent, presentDays, totalDaysInMonth);
    }
}

