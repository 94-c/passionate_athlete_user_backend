package com.backend.athlete.user.physical.dto.request;

import com.backend.athlete.user.physical.domain.Physical;
import com.backend.athlete.user.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CreatePhysicalRequest {

    @NotNull(message = "신장을 입력 해주세요.")
    private Double height;

    @NotNull(message = "체중을 입력 해주세요.")
    private Double weight;

    @NotNull(message = "골격근량을 입력 해주세요.")
    private Double muscleMass;

    @NotNull(message = "체지방량을 입력 해주세요.")
    private Double bodyFatMass;

    private LocalDateTime measureDate;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double visceralFatPercentage;
    private Double bmr;
    private Double weightChange;
    private Double heightChange;
    private Double muscleMassChange;
    private Double bodyFatMassChange;

    protected CreatePhysicalRequest() {}

    public static Physical toEntity(CreatePhysicalRequest request, User user) {
        return new Physical(
                user,
                request.getWeight(),
                request.getHeight(),
                request.getMuscleMass(),
                request.getBodyFatMass(),
                request.getMeasureDate(),
                request.getBmi(),
                request.getBodyFatPercentage(),
                request.getVisceralFatPercentage(),
                request.getBmr(),
                request.getWeightChange(),
                request.getHeightChange(),
                request.getMuscleMassChange(),
                request.getBodyFatMassChange()
        );
    }
}

