package com.backend.athlete.domain.athlete.dto.response;

import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class GetDailyAthleteResponse {

    private LocalDate dailyTime;
    private String athletics;
    private String type;
    private LocalTime record;
    private Integer round;
    private String etc;
    private String username;

    public GetDailyAthleteResponse(LocalDate dailyTime, String athletics, String type, LocalTime record, Integer round, String etc, String username) {
        this.dailyTime = dailyTime;
        this.athletics = athletics;
        this.type = type;
        this.record = record;
        this.round = round;
        this.etc = etc;
        this.username = username;
    }

    public static GetDailyAthleteResponse fromEntity(Athlete athlete) {
        User findUser = athlete.getUser();
        return new GetDailyAthleteResponse(
                athlete.getDailyTime(),
                athlete.getAthletics(),
                athlete.getType().toString(),
                athlete.getRecord(),
                athlete.getRound(),
                athlete.getEtc(),
                findUser.getName()
        );
    }
}
