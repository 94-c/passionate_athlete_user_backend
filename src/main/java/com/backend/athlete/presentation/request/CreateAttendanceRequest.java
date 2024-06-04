package com.backend.athlete.presentation.request;

import com.backend.athlete.domain.attendance.Attendance;
import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateAttendanceRequest {

    private LocalDate eventDate;

    protected CreateAttendanceRequest() {}

    public static Attendance toEntity(CreateAttendanceRequest request, User user) {
        return new Attendance(
                user,
                request.getEventDate()
        );
    }

}
