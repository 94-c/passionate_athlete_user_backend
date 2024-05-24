package com.backend.athlete.domain.physical.dto.response;

import com.backend.athlete.domain.physical.model.Physical;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class SavePhysicalResponse {

    private Long id;
    private String username;
    private Double weight;
    private Double height;
    private Double muscleMass;
    private Double bodyFatMass;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double visceralFatPercentage;
    private Double bmr;
    private LocalDate measureDate;

    public SavePhysicalResponse(Long id, String username, Double weight, Double height, Double muscleMass, Double bodyFatMass, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, LocalDate measureDate) {
        this.id = id;
        this.username = username;
        this.weight = weight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.bodyFatMass = bodyFatMass;
        this.bmi = bmi;
        this.bodyFatPercentage = bodyFatPercentage;
        this.visceralFatPercentage = visceralFatPercentage;
        this.bmr = bmr;
        this.measureDate = measureDate;
    }

    //Entity -> Dto
    public static SavePhysicalResponse fromEntity(Physical physical) {
        User findUser = physical.getUser();
        return new SavePhysicalResponse(
                physical.getId(),
                findUser.getName(),
                physical.getWeight(),
                physical.getHeight(),
                physical.getMuscleMass(),
                physical.getBodyFatMass(),
                physical.getBmi(),
                physical.getBodyFatPercentage(),
                physical.getVisceralFatPercentage(),
                physical.getBmr(),
                physical.getMeasureDate()
        );
    }
}
