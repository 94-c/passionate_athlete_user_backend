package com.backend.athlete.application;

import com.backend.athlete.domain.attendance.Attendance;
import com.backend.athlete.domain.attendance.AttendanceRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.presentation.attendance.request.CreateAttendanceRequest;
import com.backend.athlete.presentation.attendance.response.CreateAttendanceResponse;
import com.backend.athlete.presentation.attendance.response.GetDailyAttendanceResponse;
import com.backend.athlete.presentation.attendance.response.GetMonthlyAttendanceResponse;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public CreateAttendanceResponse createAttendanceEvent(CustomUserDetailsImpl userPrincipal, CreateAttendanceRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        if (request.getEventDate() == null) request.setEventDate(LocalDate.now());

        LocalDate eventDate = request.getEventDate();

        if (attendanceRepository.findByUserIdAndAttendanceDate(user.getId(), eventDate).isPresent()) {
            throw new ServiceException("이미 해당 " +  eventDate + " 일에 출석 했습니다. ");
        }

        Attendance attendance = attendanceRepository.save(CreateAttendanceRequest.toEntity(request, user));

        long totalAttendanceCount = attendanceRepository.countByUserId(user.getId());

        return CreateAttendanceResponse.fromEntity(attendance, totalAttendanceCount);
    }

    @Transactional
    public GetDailyAttendanceResponse getAttendance(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Attendance attendance = attendanceRepository.findByUserIdAndAttendanceDate(user.getId(), dailyDate)
                .orElseThrow(() -> new ServiceException(dailyDate + " 의 출석이 없습니다."));


        return GetDailyAttendanceResponse.fromEntity(attendance);
    }

    @Transactional
    public GetMonthlyAttendanceResponse getMonthlyAttendance(CustomUserDetailsImpl userPrincipal, YearMonth month) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        List<Attendance> attendances = attendanceRepository.findByUserAndAttendanceDateBetween(user, startDate, endDate);

        List<LocalDate> presentDays = attendances.stream()
                .map(Attendance::getAttendanceDate)
                .collect(Collectors.toList());

        return new GetMonthlyAttendanceResponse(presentDays.size(), presentDays);
    }

}
