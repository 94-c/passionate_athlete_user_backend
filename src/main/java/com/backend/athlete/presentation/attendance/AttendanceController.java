package com.backend.athlete.presentation.attendance;

import com.backend.athlete.application.AttendanceService;
import com.backend.athlete.presentation.attendance.request.CreateAttendanceRequest;
import com.backend.athlete.presentation.attendance.response.CreateAttendanceResponse;
import com.backend.athlete.presentation.attendance.response.GetContinuousAttendanceResponse;
import com.backend.athlete.presentation.attendance.response.GetDailyAttendanceResponse;
import com.backend.athlete.presentation.attendance.response.GetMonthlyAttendanceResponse;
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
                                                                    @RequestBody CreateAttendanceRequest request) {
        CreateAttendanceResponse response = attendanceService.createAttendanceEvent(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<GetDailyAttendanceResponse> getDailyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                    @RequestParam(name = "daily", required = false) LocalDate dailyDate) {
        GetDailyAttendanceResponse response = attendanceService.getAttendance(userPrincipal, dailyDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyAttendanceResponse> getMonthlyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                             @RequestParam(name = "month")YearMonth month) {

        GetMonthlyAttendanceResponse response = attendanceService.getMonthlyAttendance(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/continue")
    public ResponseEntity<GetContinuousAttendanceResponse> getContinuousAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetContinuousAttendanceResponse response = attendanceService.continuousAttendance(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
