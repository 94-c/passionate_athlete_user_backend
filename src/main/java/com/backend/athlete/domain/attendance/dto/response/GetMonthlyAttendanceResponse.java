package com.backend.athlete.domain.attendance.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetMonthlyAttendanceResponse {

    private int totalDaysPresent;
    private List<LocalDate> presentDays;

    public GetMonthlyAttendanceResponse(int totalDaysPresent, List<LocalDate> presentDays) {
        this.totalDaysPresent = totalDaysPresent;
        this.presentDays = presentDays;
    }

    // Entity -> Dto
    public static GetMonthlyAttendanceResponse fromEntity(int totalDaysPresent, List<LocalDate> presentDays) {
        return new GetMonthlyAttendanceResponse(totalDaysPresent, presentDays);
    }
}
