package com.backend.athlete.domain.attendance.controller;

import com.backend.athlete.domain.attendance.dto.request.CreateAttendanceEventRequest;
import com.backend.athlete.domain.attendance.dto.response.CreateAttendanceEventResponse;
import com.backend.athlete.domain.attendance.service.AttendanceService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<CreateAttendanceEventResponse> dailyAttendance(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                         @RequestBody CreateAttendanceEventRequest dto) {
        CreateAttendanceEventResponse dailyAttendance = attendanceService.createAttendanceEvent(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyAttendance);
    }

}
