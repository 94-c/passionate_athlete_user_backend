package com.backend.athlete.presentation.response;

import com.backend.athlete.domain.attendance.Attendance;
import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
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
