package com.backend.athlete.domain.athlete.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;

@Getter @Setter
public class GetMonthlyAthleteResponseDto {

    private YearMonth month;
    private List<GetDailyAthleteResponseDto> dailyRecords;

    public GetMonthlyAthleteResponseDto(YearMonth yearMonth, List<GetDailyAthleteResponseDto> dailyRecords) {
        this.month = yearMonth;
        this.dailyRecords = dailyRecords;
    }

    // Entity -> Dto
    public static GetMonthlyAthleteResponseDto fromEntity(YearMonth month, List<GetDailyAthleteResponseDto> dailyRecords) {
        return new GetMonthlyAthleteResponseDto(month, dailyRecords);
    }

}
