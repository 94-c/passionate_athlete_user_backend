package com.backend.athlete.domain.report.controller;

import com.backend.athlete.domain.report.application.ReportService;
import com.backend.athlete.domain.report.dto.response.GetWeeklyAttendanceResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;

@Controller
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/weekly")
    public ResponseEntity<GetWeeklyAttendanceResponse> getWeeklyAttendance(
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
            @RequestParam LocalDate week) {

        GetWeeklyAttendanceResponse response = reportService.getWeeklyAttendance(userPrincipal, week);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/monthly")
    public ResponseEntity<GetWeeklyAttendanceResponse> getMonthlyAttendance(
            @AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
            @RequestParam YearMonth month) {

        GetWeeklyAttendanceResponse response = reportService.getMonthlyAttendance(userPrincipal, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
