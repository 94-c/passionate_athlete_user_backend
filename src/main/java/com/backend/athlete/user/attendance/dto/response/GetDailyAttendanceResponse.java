package com.backend.athlete.user.attendance.dto.response;

import com.backend.athlete.user.attendance.domain.Attendance;
import com.backend.athlete.user.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetDailyAttendanceResponse {

    private Long id;
    private String username;
    private LocalDate attendanceDate;

    public GetDailyAttendanceResponse(Long id, String username, LocalDate attendanceDate) {
        this.id = id;
        this.username = username;
        this.attendanceDate = attendanceDate;
    }

    // Entity -> Dto
    public static GetDailyAttendanceResponse fromEntity(Attendance attendance) {
        User findUser = attendance.getUser();
        return new GetDailyAttendanceResponse(
                attendance.getId(),
                findUser.getName(),
                attendance.getAttendanceDate()
        );
    }

}
