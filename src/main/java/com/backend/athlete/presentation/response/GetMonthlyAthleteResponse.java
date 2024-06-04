package com.backend.athlete.presentation.response;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;

@Getter @Setter
public class GetMonthlyAthleteResponse {

    private YearMonth month;
    private List<GetDailyAthleteResponse> dailyRecords;

    public GetMonthlyAthleteResponse(YearMonth yearMonth, List<GetDailyAthleteResponse> dailyRecords) {
        this.month = yearMonth;
        this.dailyRecords = dailyRecords;
    }

    // Entity -> Dto
    public static GetMonthlyAthleteResponse fromEntity(YearMonth month, List<GetDailyAthleteResponse> dailyRecords) {
        return new GetMonthlyAthleteResponse(month, dailyRecords);
    }

}
