package com.backend.athlete.user.physical.dto.request;

import com.backend.athlete.user.physical.domain.Physical;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UpdatePhysicalRequest {

    private Double height;         // 신장
    private Double weight;         // 체중
    private Double muscleMass;     // 골격근량
    private Double bodyFatMass;    // 체지방량
    private LocalDateTime measureDate;
    private Double bmi;
    private Double bodyFatPercentage;
    private Double visceralFatPercentage;
    private Double bmr;
    private Double weightChange;
    private Double heightChange;
    private Double muscleMassChange;
    private Double bodyFatMassChange;

    protected UpdatePhysicalRequest() {}

    public void fromEntity(Physical physical, Physical previousPhysical) {
        if (this.weight != null || this.muscleMass != null || this.bodyFatMass != null) {
            Double updatedWeight = this.weight != null ? this.weight : physical.getWeight();
            Double updatedMuscleMass = this.muscleMass != null ? this.muscleMass : physical.getMuscleMass();
            Double updatedBodyFatMass = this.bodyFatMass != null ? this.bodyFatMass : physical.getBodyFatMass();

            physical.updateWithNewValues(updatedWeight, updatedMuscleMass, updatedBodyFatMass, previousPhysical);
        }

    }
}

