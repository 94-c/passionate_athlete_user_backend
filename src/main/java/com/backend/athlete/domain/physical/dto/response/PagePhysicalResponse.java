package com.backend.athlete.domain.physical.dto.response;

import com.backend.athlete.domain.physical.domain.Physical;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PagePhysicalResponse {
    private Long id;
    private Double weight;
    private Double height;
    private Double muscleMass;
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

    public PagePhysicalResponse(Long id, Double weight, Double height, Double muscleMass, Double bodyFatMass, LocalDateTime measureDate, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, Double weightChange, Double heightChange, Double muscleMassChange, Double bodyFatMassChange) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.bodyFatMass = bodyFatMass;
        this.measureDate = measureDate;
        this.bmi = bmi;
        this.bodyFatPercentage = bodyFatPercentage;
        this.visceralFatPercentage = visceralFatPercentage;
        this.bmr = bmr;
        this.weightChange = weightChange;
        this.heightChange = heightChange;
        this.muscleMassChange = muscleMassChange;
        this.bodyFatMassChange = bodyFatMassChange;
    }

    // Entity-> Dto
    public static PagePhysicalResponse fromEntity(Physical physical) {
        return new PagePhysicalResponse(
                physical.getId(),
                physical.getWeight(),
                physical.getHeight(),
                physical.getMuscleMass(),
                physical.getBodyFatMass(),
                physical.getMeasureDate(),
                physical.getBmi(),
                physical.getBodyFatPercentage(),
                physical.getVisceralFatPercentage(),
                physical.getBmr(),
                physical.getWeightChange(),
                physical.getHeightChange(),
                physical.getMuscleMassChange(),
                physical.getBodyFatMassChange()
        );
    }
}

