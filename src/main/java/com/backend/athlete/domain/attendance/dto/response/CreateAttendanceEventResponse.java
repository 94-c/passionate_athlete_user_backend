package com.backend.athlete.domain.attendance.dto.response;

import com.backend.athlete.domain.attendance.model.Attendance;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateAttendanceEventResponse {

    private String userName;
    private LocalDate attendanceDate;
    private long totalAttendanceCount;

    public CreateAttendanceEventResponse(String userName, LocalDate attendanceDate, long totalAttendanceCount) {
        this.userName = userName;
        this.attendanceDate = attendanceDate;
        this.totalAttendanceCount = totalAttendanceCount;
    }

    //Entity -> Dto
    public static CreateAttendanceEventResponse fromEntity(Attendance attendance, long totalAttendanceCount) {
        User findUser = attendance.getUser();
        return new CreateAttendanceEventResponse(
                findUser.getName(),
                attendance.getAttendanceDate(),
                totalAttendanceCount
        );
    }
}
