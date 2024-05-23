package com.backend.athlete.domain.athlete.service;

import com.backend.athlete.domain.athlete.dto.GroupedAthleteRecordDto;
import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequestDto;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponseDto;
import com.backend.athlete.domain.athlete.dto.response.GetDailyAthleteResponseDto;
import com.backend.athlete.domain.athlete.dto.response.GetMonthlyAthleteResponseDto;
import com.backend.athlete.domain.athlete.model.Athlete;
import com.backend.athlete.domain.athlete.repository.AthleteRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final UserRepository userRepository;

    public AthleteService(AthleteRepository athleteRepository, UserRepository userRepository) {
        this.athleteRepository = athleteRepository;
        this.userRepository = userRepository;
    }

    /**
     * 데일리 운동 기록 저장
     */
    public CreateAthleteResponseDto createAthlete(CustomUserDetailsImpl userPrincipal, CreateAthleteRequestDto dto) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        dto.setDailyTime(LocalDate.now());
        Athlete createAthlete = athleteRepository.save(CreateAthleteRequestDto.toEntity(dto, findUser));

        return CreateAthleteResponseDto.fromEntity(createAthlete);
    }

    /**
     * 데일리 운동 기록 조회
     */
    @Transactional
    public GetDailyAthleteResponseDto getDailyAthlete(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        List<Athlete> athletes = athleteRepository.findAthletesByUserIdAndDailyTime(findUser.getId(), dailyDate);

        if (athletes.isEmpty()) {
            throw new EntityNotFoundException("No athlete found for the given user and date.");
        }

        Athlete athlete = athletes.get(0);

        return GetDailyAthleteResponseDto.fromEntity(athlete);
    }

    /**
     * 월별 운동 기록 조회
     */
    @Transactional
    public GetMonthlyAthleteResponseDto getMonthlyAthlete(CustomUserDetailsImpl userPrincipal, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        List<GroupedAthleteRecordDto> groupedAthleteRecords = athleteRepository.findGroupedAthletesByUserIdAndYearMonth(findUser.getId(), startDate, endDate);

        List<GetDailyAthleteResponseDto> monthlyRecords = groupedAthleteRecords.stream()
                .map(record -> new GetDailyAthleteResponseDto(
                        record.getDailyTime(),
                        record.getAthletics(),
                        record.getType().toString(), // Enum을 문자열로 변환
                        record.getTotalRecordAsLocalTime(),
                        (int) record.getCount(),
                        record.getEtc(),
                        record.getUsername()
                ))
                .collect(Collectors.toList());

        return GetMonthlyAthleteResponseDto.fromEntity(yearMonth, monthlyRecords);
    }

    public void deleteDailyAthleteById(CustomUserDetailsImpl userPrincipal, Long id) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        Athlete athlete = athleteRepository.findByIdAndUserId(id, findUser.getId())
                .orElseThrow(() -> new RuntimeException("Athlete record not found or not authorized"));

        athleteRepository.delete(athlete);
    }
}
