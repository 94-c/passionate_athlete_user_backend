package com.backend.athlete.presentation.physical.response;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class GetPhysicalResponse {

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
    private LocalDateTime measureDate;
    private Double weightChange;
    private Double heightChange;
    private Double muscleMassChange;
    private Double bodyFatMassChange;

    public GetPhysicalResponse(Long id, String username, Double weight, Double height, Double muscleMass, Double bodyFatMass, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, LocalDateTime measureDate, Double weightChange, Double heightChange, Double muscleMassChange, Double bodyFatMassChange) {
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
        this.weightChange = weightChange;
        this.heightChange = heightChange;
        this.muscleMassChange = muscleMassChange;
        this.bodyFatMassChange = bodyFatMassChange;
    }

    // Entity -> Dto
    public static GetPhysicalResponse fromEntity(Physical physical, Physical previousPhysical) {
        User findUser = physical.getUser();

        Double weightChange = (previousPhysical != null) ? physical.getWeight() - previousPhysical.getWeight() : 0.0;
        Double heightChange = (previousPhysical != null) ? physical.getHeight() - previousPhysical.getHeight() : 0.0;
        Double muscleMassChange = (previousPhysical != null) ? physical.getMuscleMass() - previousPhysical.getMuscleMass() : 0.0;
        Double bodyFatMassChange = (previousPhysical != null) ? physical.getBodyFatMass() - previousPhysical.getBodyFatMass() : 0.0;

        return new GetPhysicalResponse(
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
                physical.getMeasureDate(),
                weightChange,
                heightChange,
                muscleMassChange,
                bodyFatMassChange
        );
    }
}