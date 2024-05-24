package com.backend.athlete.domain.attendance.repository;

import com.backend.athlete.domain.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate eventDate);

    long countByUserId(Long userId);

}
