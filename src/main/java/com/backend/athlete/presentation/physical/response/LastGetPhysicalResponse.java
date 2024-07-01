package com.backend.athlete.presentation.physical.response;

import com.backend.athlete.domain.physical.Physical;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LastGetPhysicalResponse {

    private LocalDateTime measureDate;
    private Double height;
    private Double weight;
    private Double muscleMass;
    private Double bodyFatMass;
    private Double bodyFatMassChange;
    private Double muscleMassChange;
    private Double weightChange;

    public LastGetPhysicalResponse(LocalDateTime measureDate, Double height, Double weight, Double muscleMass, Double bodyFatMass, Double bodyFatMassChange, Double muscleMassChange, Double weightChange) {
        this.measureDate = measureDate;
        this.height = height;
        this.weight = weight;
        this.muscleMass = muscleMass;
        this.bodyFatMass = bodyFatMass;
        this.bodyFatMassChange = bodyFatMassChange;
        this.muscleMassChange = muscleMassChange;
        this.weightChange = weightChange;
    }

    public static LastGetPhysicalResponse fromEntity(Physical physical) {
        return new LastGetPhysicalResponse(
                physical.getMeasureDate(),
                physical.getHeight(),
                physical.getWeight(),
                physical.getMuscleMass(),
                physical.getBodyFatMass(),
                physical.getBodyFatMassChange(),
                physical.getMuscleMassChange(),
                physical.getWeightChange()
        );
    }

}
