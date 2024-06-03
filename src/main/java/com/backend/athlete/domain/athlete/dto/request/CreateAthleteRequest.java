package com.backend.athlete.domain.athlete.dto.request;

import com.backend.athlete.domain.athlete.model.Athlete;
import com.backend.athlete.domain.athlete.model.type.AthleteSuccessType;
import com.backend.athlete.domain.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class CreateAthleteRequest {

    private LocalDate dailyTime;
    @NotBlank(message = "운동 일지를 작성 해주세요.")
    private String athletics;
    @NotNull(message = "운동 성공 여부를 입력 해주세요.")
    private AthleteSuccessType type;
    @NotNull(message = "운동 시간을 작성해주세요.")
    private LocalTime record;
    @NotNull(message = "운동 라운드를 작성해주세요.")
    private Integer round;

    private String etc;

    protected CreateAthleteRequest() {}

    public static Athlete toEntity(CreateAthleteRequest dto, User user) {
        return new Athlete(
                user,
                dto.getDailyTime(),
                dto.getAthletics(),
                dto.getType(),
                dto.getRecord(),
                dto.getRound(),
                dto.getEtc()
        );
    }


}
