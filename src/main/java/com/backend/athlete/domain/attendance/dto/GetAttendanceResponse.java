package com.backend.athlete.domain.attendance.dto;

import com.backend.athlete.domain.attendance.model.Attendance;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class GetAttendanceResponse {

    private Long id;
    private String username;
    private LocalDate attendanceDate;

    public GetAttendanceResponse(Long id, String username, LocalDate attendanceDate) {
        this.id = id;
        this.username = username;
        this.attendanceDate = attendanceDate;
    }

    // Entity -> Dto
    public static GetAttendanceResponse fromEntity(Attendance attendance) {
        User findUser = attendance.getUser();
        return new GetAttendanceResponse(
                attendance.getId(),
                findUser.getName(),
                attendance.getAttendanceDate()
        );
    }

}
