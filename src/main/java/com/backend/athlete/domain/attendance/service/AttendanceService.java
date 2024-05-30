package com.backend.athlete.domain.attendance.service;

import com.backend.athlete.domain.attendance.dto.GetAttendanceResponse;
import com.backend.athlete.domain.attendance.dto.request.CreateAttendanceEventRequest;
import com.backend.athlete.domain.attendance.dto.response.CreateAttendanceEventResponse;
import com.backend.athlete.domain.attendance.model.Attendance;
import com.backend.athlete.domain.attendance.repository.AttendanceRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ServiceException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public CreateAttendanceEventResponse createAttendanceEvent(CustomUserDetailsImpl userPrincipal, CreateAttendanceEventRequest dto) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());
        if (user == null) {
            throw new ServiceException("회원을 찾을 수 없습니다.");
        }

        if (dto.getEventDate() == null) dto.setEventDate(LocalDate.now());

        LocalDate eventDate = dto.getEventDate();

        if (attendanceRepository.findByUserIdAndAttendanceDate(user.getId(), eventDate).isPresent()) {
            throw new ServiceException("이미 해당 " +  eventDate + " 일에 출석 했습니다. ");
        }

        Attendance attendance = attendanceRepository.save(CreateAttendanceEventRequest.toEntity(dto, user));

        long totalAttendanceCount = attendanceRepository.countByUserId(user.getId());

        return CreateAttendanceEventResponse.fromEntity(attendance, totalAttendanceCount);
    }

    @Transactional
    public GetAttendanceResponse getAttendance(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        Attendance attendance = attendanceRepository.findByUserIdAndAttendanceDate(findUser.getId(), dailyDate)
                .orElseThrow(() -> new ServiceException(dailyDate + " 의 출석이 없습니다."));


        return GetAttendanceResponse.fromEntity(attendance);
    }

}
