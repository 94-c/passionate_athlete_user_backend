package com.backend.athlete.user.athlete.dto.request;

import com.backend.athlete.user.athlete.domain.Athlete;
import com.backend.athlete.user.athlete.domain.type.AthleteSuccessType;
import com.backend.athlete.user.user.domain.User;
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

    public static Athlete toEntity(CreateAthleteRequest request, User user) {
        return new Athlete(
                user,
                request.getDailyTime(),
                request.getAthletics(),
                request.getType(),
                request.getRecord(),
                request.getRound(),
                request.getEtc()
        );
    }


}
