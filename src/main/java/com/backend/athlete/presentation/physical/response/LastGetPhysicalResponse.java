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

    public LastGetPhysicalResponse(LocalDateTime measureDate, Double height, Double weight, Double muscleMass, Double bodyFatMass) {
        this.measureDate = measureDate;
        this.height = height;
        this.weight = weight;
        this.muscleMass = muscleMass;
        this.bodyFatMass = bodyFatMass;
    }

    public static LastGetPhysicalResponse fromEntity(Physical physical) {
        return new LastGetPhysicalResponse(
                physical.getMeasureDate(),
                physical.getHeight(),
                physical.getWeight(),
                physical.getMuscleMass(),
                physical.getBodyFatMass()
        );
    }

}
