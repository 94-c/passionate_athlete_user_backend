package com.backend.athlete.user.attendance.dto.response;

import com.backend.athlete.user.attendance.domain.Attendance;
import com.backend.athlete.user.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateAttendanceResponse {

    private String userName;
    private LocalDate attendanceDate;
    private long totalAttendanceCount;

    public CreateAttendanceResponse(String userName, LocalDate attendanceDate, long totalAttendanceCount) {
        this.userName = userName;
        this.attendanceDate = attendanceDate;
        this.totalAttendanceCount = totalAttendanceCount;
    }

    //Entity -> Dto
    public static CreateAttendanceResponse fromEntity(Attendance attendance, long totalAttendanceCount) {
        User findUser = attendance.getUser();
        return new CreateAttendanceResponse(
                findUser.getName(),
                attendance.getAttendanceDate(),
                totalAttendanceCount
        );
    }
}
