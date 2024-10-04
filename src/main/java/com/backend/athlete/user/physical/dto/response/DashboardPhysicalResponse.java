package com.backend.athlete.user.physical.dto.response;

import com.backend.athlete.user.physical.domain.Physical;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DashboardPhysicalResponse {

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

    public DashboardPhysicalResponse(Double weight, Double height, Double muscleMass, Double bodyFatMass, LocalDateTime measureDate, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, Double weightChange, Double heightChange, Double muscleMassChange, Double bodyFatMassChange) {
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
    public static DashboardPhysicalResponse fromEntity(Physical physical, Physical previousPhysical) {
        Double weightChange = (previousPhysical != null) ? physical.getWeight() - previousPhysical.getWeight() : 0.0;
        Double heightChange = (previousPhysical != null) ? physical.getHeight() - previousPhysical.getHeight() : 0.0;
        Double muscleMassChange = (previousPhysical != null) ? physical.getMuscleMass() - previousPhysical.getMuscleMass() : 0.0;
        Double bodyFatMassChange = (previousPhysical != null) ? physical.getBodyFatMass() - previousPhysical.getBodyFatMass() : 0.0;

        return new DashboardPhysicalResponse(
                physical.getWeight(),
                physical.getHeight(),
                physical.getMuscleMass(),
                physical.getBodyFatMass(),
                physical.getMeasureDate(),
                physical.getBmi(),
                physical.getBodyFatPercentage(),
                physical.getVisceralFatPercentage(),
                physical.getBmr(),
                weightChange,
                heightChange,
                muscleMassChange,
                bodyFatMassChange
        );
    }
}
