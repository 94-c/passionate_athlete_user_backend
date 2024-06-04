package com.backend.athlete.presentation;

import com.backend.athlete.application.AttendanceService;
import com.backend.athlete.presentation.request.CreateAttendanceRequest;
import com.backend.athlete.presentation.response.CreateAttendanceResponse;
import com.backend.athlete.presentation.response.GetDailyAttendanceResponse;
import com.backend.athlete.presentation.response.GetMonthlyAttendanceResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@Controller
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<CreateAttendanceResponse> dailyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                    @RequestBody CreateAttendanceRequest dto) {
        CreateAttendanceResponse dailyAttendance = attendanceService.createAttendanceEvent(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyAttendance);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetDailyAttendanceResponse> getDailyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                    @RequestParam(name = "daily", required = false) LocalDate dailyDate) {
        GetDailyAttendanceResponse getAttendance = attendanceService.getAttendance(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(getAttendance);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyAttendanceResponse> getMonthlyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                             @RequestParam(name = "month")YearMonth month) {

        GetMonthlyAttendanceResponse getMonthlyAttendance = attendanceService.getMonthlyAttendance(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(getMonthlyAttendance);
    }

}
