package com.backend.athlete.application;

import com.backend.athlete.domain.athlete.Athlete;
import com.backend.athlete.domain.athlete.AthleteRepository;
import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.presentation.athlete.request.CreateAthleteRequest;
import com.backend.athlete.presentation.athlete.response.CreateAthleteResponse;
import com.backend.athlete.presentation.athlete.response.GetDailyAthleteResponse;
import com.backend.athlete.presentation.athlete.response.GetMonthlyAthleteResponse;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AthleteService {

    private final AthleteRepository athleteRepository;
    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public CreateAthleteResponse createAthlete(CustomUserDetailsImpl userPrincipal, CreateAthleteRequest request) {
        User user = FindUtil.findByUserId(userPrincipal.getUsername());

        request.setDailyTime(LocalDate.now());

        Athlete createAthlete = athleteRepository.save(CreateAthleteRequest.toEntity(request, user));

        return CreateAthleteResponse.fromEntity(createAthlete);
    }

    @Transactional
    public GetDailyAthleteResponse getDailyAthlete(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User user = FindUtil.findByUserId(userPrincipal.getUsername());

        List<Athlete> athletes = athleteRepository.findAthletesByUserIdAndDailyTime(user.getId(), dailyDate);

        if (athletes.isEmpty()) {
            throw new EntityNotFoundException("No athlete found for the given user and date.");
        }

        Athlete athlete = athletes.get(0);

        return GetDailyAthleteResponse.fromEntity(athlete);
    }

    @Transactional
    public GetMonthlyAthleteResponse getMonthlyAthlete(CustomUserDetailsImpl userPrincipal, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        User user = FindUtil.findByUserId(userPrincipal.getUsername());

        List<AthleteData> groupedAthleteRecords = athleteRepository.findGroupedAthletesByUserIdAndYearMonth(user.getId(), startDate, endDate);

        List<GetDailyAthleteResponse> monthlyRecords = groupedAthleteRecords.stream()
                .map(record -> new GetDailyAthleteResponse(
                        record.getDailyTime(),
                        record.getAthletics(),
                        record.getType().toString(),
                        record.getTotalRecordAsLocalTime(),
                        (int) record.getCount(),
                        record.getEtc(),
                        record.getUsername()
                ))
                .collect(Collectors.toList());

        return GetMonthlyAthleteResponse.fromEntity(yearMonth, monthlyRecords);
    }

    public void deleteDailyAthleteById(CustomUserDetailsImpl userPrincipal, Long id) {
        User user = FindUtil.findByUserId(userPrincipal.getUsername());

        Athlete athlete = athleteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Athlete record not found or not authorized"));

        athleteRepository.delete(athlete);
    }
}
