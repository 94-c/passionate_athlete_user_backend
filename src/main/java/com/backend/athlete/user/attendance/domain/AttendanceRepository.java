package com.backend.athlete.user.attendance.domain;

import com.backend.athlete.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate eventDate);
    long countByUserId(Long userId);
    List<Attendance> findByUserAndAttendanceDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Attendance> findAllByUserIdOrderByAttendanceDateAsc(Long userId);
    long countByUserIdAndAttendanceDateBetween(Long userId, LocalDate start, LocalDate end);
    void deleteByUserAndAttendanceDate(User user, LocalDate attendanceDate);
}
