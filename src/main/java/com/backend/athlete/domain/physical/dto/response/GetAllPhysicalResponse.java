package com.backend.athlete.domain.physical.dto.response;

import com.backend.athlete.domain.physical.model.Physical;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class GetAllPhysicalResponse {

    private Double weight;
    private Double height;
    private Double muscleMass;
    private Double bodyFatMass;
    private LocalDate measureDate;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double visceralFatPercentage;
    private Double bmr;
    private Double weightChange;
    private Double heightChange;
    private Double muscleMassChange;
    private Double bodyFatMassChange;

    public GetAllPhysicalResponse(Double weight, Double height, Double muscleMass, Double bodyFatMass, LocalDate measureDate, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, Double weightChange, Double heightChange, Double muscleMassChange, Double bodyFatMassChange) {
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
    public static GetAllPhysicalResponse fromEntity(Physical physical, Physical previousPhysical) {
        Double weightChange = (previousPhysical != null) ? physical.getWeight() - previousPhysical.getWeight() : 0.0;
        Double heightChange = (previousPhysical != null) ? physical.getHeight() - previousPhysical.getHeight() : 0.0;
        Double muscleMassChange = (previousPhysical != null) ? physical.getMuscleMass() - previousPhysical.getMuscleMass() : 0.0;
        Double bodyFatMassChange = (previousPhysical != null) ? physical.getBodyFatMass() - previousPhysical.getBodyFatMass() : 0.0;

        return new GetAllPhysicalResponse(
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

