package com.backend.athlete.user.attendance.dto.request;

import com.backend.athlete.user.attendance.domain.Attendance;
import com.backend.athlete.user.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateAttendanceRequest {

    private LocalDate eventDate;

    public CreateAttendanceRequest(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public static Attendance toEntity(CreateAttendanceRequest request, User user) {
        LocalDate eventDate = request.getEventDate() != null ? request.getEventDate() : LocalDate.now();
        return new Attendance(
                user,
                eventDate
        );
    }

}
