package com.backend.athlete.user.athlete.dto.response;

import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
public class GetMonthlyAthleteResponse {

    private YearMonth month;
    private List<GetDailyAthleteResponse> dailyRecords;

    public GetMonthlyAthleteResponse(YearMonth yearMonth, List<GetDailyAthleteResponse> dailyRecords) {
        this.month = yearMonth;
        this.dailyRecords = dailyRecords;
    }

    public static GetMonthlyAthleteResponse fromEntity(YearMonth month, List<GetDailyAthleteResponse> dailyRecords) {
        return new GetMonthlyAthleteResponse(month, dailyRecords);
    }

}
