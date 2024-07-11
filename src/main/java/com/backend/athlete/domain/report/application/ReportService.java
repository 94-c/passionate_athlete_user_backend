package com.backend.athlete.domain.report.application;

import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.athlete.domain.AthleteRepository;
import com.backend.athlete.domain.athlete.domain.type.AthleteSuccessType;
import com.backend.athlete.domain.attendance.domain.Attendance;
import com.backend.athlete.domain.attendance.domain.AttendanceRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.report.dto.response.GetWeeklyAttendanceResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AttendanceRepository attendanceRepository;
    private final AthleteRepository athleteRepository;

    @Transactional
    public GetWeeklyAttendanceResponse getWeeklyAttendance(CustomUserDetailsImpl userPrincipal, LocalDate week) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        LocalDate startOfWeek = week.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = week.with(java.time.DayOfWeek.SUNDAY);

        return getAttendanceData(user, startOfWeek, endOfWeek);
    }

    @Transactional
    public GetWeeklyAttendanceResponse getMonthlyAttendance(CustomUserDetailsImpl userPrincipal, YearMonth month) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        return getAttendanceData(user, startDate, endDate);
    }

    private GetWeeklyAttendanceResponse getAttendanceData(User user, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findByUserAndAttendanceDateBetween(user, startDate, endDate);
        List<Athlete> athletes = athleteRepository.findByUserAndDailyTimeBetween(user, startDate, endDate);

        List<LocalDate> presentDays = attendances.stream()
                .map(Attendance::getAttendanceDate)
                .collect(Collectors.toList());

        List<LocalDate> successDays = athletes.stream()
                .filter(athlete -> athlete.getType() == AthleteSuccessType.SUCCESS)
                .map(Athlete::getDailyTime)
                .collect(Collectors.toList());

        List<LocalDate> failDays = athletes.stream()
                .filter(athlete -> athlete.getType() == AthleteSuccessType.FAIL)
                .map(Athlete::getDailyTime)
                .collect(Collectors.toList());

        int totalDaysPresent = presentDays.size();
        int totalDaysSuccess = successDays.size();
        int totalDaysFail = failDays.size();

        return GetWeeklyAttendanceResponse.fromEntity(totalDaysPresent, totalDaysSuccess, totalDaysFail, presentDays, successDays, failDays);
    }

}
