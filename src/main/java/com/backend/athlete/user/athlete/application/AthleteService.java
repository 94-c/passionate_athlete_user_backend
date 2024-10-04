package com.backend.athlete.user.athlete.application;

import com.backend.athlete.user.athlete.domain.Athlete;
import com.backend.athlete.user.athlete.domain.AthleteRepository;
import com.backend.athlete.user.athlete.data.AthleteData;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.athlete.dto.response.GetDailyAthleteResponse;
import com.backend.athlete.user.athlete.dto.response.GetMonthlyAthleteResponse;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.exception.DeleteDailyAthleteException;
import com.backend.athlete.support.exception.IsNotEmptyAthleteException;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
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
