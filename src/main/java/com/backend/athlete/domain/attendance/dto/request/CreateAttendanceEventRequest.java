package com.backend.athlete.domain.attendance.dto.request;

import com.backend.athlete.domain.attendance.model.Attendance;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateAttendanceEventRequest {

    private LocalDate eventDate;

    protected CreateAttendanceEventRequest() {}

    public static Attendance toEntity(CreateAttendanceEventRequest dto, User user) {
        return new Attendance(
                user,
                dto.getEventDate()
        );
    }

}
