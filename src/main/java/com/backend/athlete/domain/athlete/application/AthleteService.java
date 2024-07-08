package com.backend.athlete.domain.athlete.application;

import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.athlete.domain.AthleteRepository;
import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequest;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponse;
import com.backend.athlete.domain.athlete.dto.response.GetDailyAthleteResponse;
import com.backend.athlete.domain.athlete.dto.response.GetMonthlyAthleteResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.exception.DeleteDailyAthleteException;
import com.backend.athlete.support.exception.IsNotEmptyAthleteException;
import com.backend.athlete.support.util.FindUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public GetDailyAthleteResponse getDailyAthlete(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<Athlete> athletes = athleteRepository.findAthletesByUserIdAndDailyTime(user.getId(), dailyDate);

        if (athletes.isEmpty()) {
            throw new IsNotEmptyAthleteException(HttpStatus.NOT_FOUND);
        }

        Athlete athlete = athletes.get(0);

        return GetDailyAthleteResponse.fromEntity(athlete);
    }

    public GetMonthlyAthleteResponse getMonthlyAthlete(CustomUserDetailsImpl userPrincipal, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        User user = FindUtils.findByUserId(userPrincipal.getUsername());

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
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Athlete athlete = athleteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new DeleteDailyAthleteException(HttpStatus.FORBIDDEN));

        athleteRepository.delete(athlete);
    }
}
