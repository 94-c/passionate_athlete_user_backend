package com.backend.athlete.presentation.request;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class SavePhysicalRequest {

    @NotNull(message = "체중을 입력 해주세요.")
    private Double weight;
    @NotNull(message = "몸무게를 입력 해주세요.")
    private Double height;
    @NotNull(message = "근육량을 입력 해주세요.")
    private Double muscleMass;
    @NotNull(message = "체지방량을 입력 해주세요.")
    private Double bodyFatMass;
    private LocalDate measureDate;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double visceralFatPercentage;
    private Double bmr;
    protected SavePhysicalRequest() {}

    public static Physical toEntity(SavePhysicalRequest dto, User user) {
        return new Physical(
                user,
                dto.getWeight(),
                dto.getHeight(),
                dto.getMuscleMass(),
                dto.getBodyFatMass(),
                dto.getMeasureDate(),
                dto.getBmi(),
                dto.getBodyFatPercentage(),
                dto.getVisceralFatPercentage(),
                dto.getBmr()
        );
    }
}
