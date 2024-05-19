package com.backend.athlete.domain.athlete.service;

import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequestDto;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponseDto;
import com.backend.athlete.domain.athlete.model.Athlete;
import com.backend.athlete.domain.athlete.repository.AthleteRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final UserRepository userRepository;

    public AthleteService(AthleteRepository athleteRepository, UserRepository userRepository) {
        this.athleteRepository = athleteRepository;
        this.userRepository = userRepository;
    }

    public CreateAthleteResponseDto createAthlete(CustomUserDetailsImpl userPrincipal, CreateAthleteRequestDto dto) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Athlete createAthlete = athleteRepository.save(CreateAthleteRequestDto.toEntity(dto, findUser));

        return CreateAthleteResponseDto.fromEntity(createAthlete);
    }
}
