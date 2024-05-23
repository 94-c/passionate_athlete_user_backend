package com.backend.athlete.domain.athlete.dto.response;

import com.backend.athlete.domain.athlete.model.Athlete;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class GetDailyAthleteResponseDto {

    private LocalDate dailyTime;
    private String athletics;
    private String type;
    private LocalTime record;
    private Integer round;
    private String etc;
    private String username;

    public GetDailyAthleteResponseDto(LocalDate dailyTime, String athletics, String type, LocalTime record, Integer round, String etc, String username) {
        this.dailyTime = dailyTime;
        this.athletics = athletics;
        this.type = type;
        this.record = record;
        this.round = round;
        this.etc = etc;
        this.username = username;
    }

    public static GetDailyAthleteResponseDto fromEntity(Athlete athlete) {
        User findUser = athlete.getUser();
        return new GetDailyAthleteResponseDto(
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
