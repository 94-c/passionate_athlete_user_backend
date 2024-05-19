package com.backend.athlete.domain.athlete.service;

import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequestDto;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponseDto;
import com.backend.athlete.domain.athlete.dto.response.GetDailyAthleteResponseDto;
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
import java.util.List;

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


}
