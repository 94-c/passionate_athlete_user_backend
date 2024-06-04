package com.backend.athlete.domain.attendance;

import com.backend.athlete.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate eventDate);

    long countByUserId(Long userId);

    List<Attendance> findByUserAndAttendanceDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
