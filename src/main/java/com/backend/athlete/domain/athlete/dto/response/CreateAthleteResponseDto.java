package com.backend.athlete.domain.athlete.dto.response;

import com.backend.athlete.domain.athlete.model.Athlete;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
public class CreateAthleteResponseDto {

    private Long id;
    private LocalDate dailyTime;
    private String athletics;
    private String type;
    private LocalTime record;
    private Integer round;
    private String etc;
    private String username;

    public CreateAthleteResponseDto(Long id, LocalDate dailyTime, String athletics, String type, LocalTime record, Integer round, String etc, String username) {
        this.id = id;
        this.dailyTime = dailyTime;
        this.athletics = athletics;
        this.type = type;
        this.record = record;
        this.round = round;
        this.etc = etc;
        this.username = username;
    }

    //Entity -> Dto
    public static CreateAthleteResponseDto fromEntity(Athlete athlete) {
        User findUser = athlete.getUser();
        return new CreateAthleteResponseDto(
                athlete.getId(),
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
