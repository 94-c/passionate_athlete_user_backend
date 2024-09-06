package com.backend.athlete.domain.physical.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "physicals")
public class Physical extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Comment("체중")
    @Column(name = "weight", nullable = false)
    private Double weight;

    @Comment("키")
    @Column(name = "height", nullable = false)
    private Double height;

    @Comment("근육량")
    @Column(name = "muscle_mass")
    private Double muscleMass;

    @Comment("체지방량")
    @Column(name = "body_fat_mass")
    private Double bodyFatMass;

    @Comment("BMI")
    private Double bmi;

    @Comment("체지방률")
    @Column(name = "body_fat_percentage")
    private Double bodyFatPercentage;

    @Comment("복부지방률")
    @Column(name = "visceral_fat_percentage")
    private Double visceralFatPercentage;

    @Comment("기초대사량")
    private Double bmr;

    @Comment("측정 날짜")
    @CreatedDate
    @Column(name = "measure_date")
    private LocalDateTime measureDate;

    @Comment("체중 변화")
    private Double weightChange;

    @Comment("키 변화")
    private Double heightChange;

    @Comment("근육량 변화")
    private Double muscleMassChange;

    @Comment("체지방량 변화")
    private Double bodyFatMassChange;

    public Physical(User user, Double weight, Double height, Double muscleMass, Double bodyFatMass, LocalDateTime measureDate, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr, Double weightChange, Double heightChange, Double muscleMassChange, Double bodyFatMassChange) {
        this.user = user;
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

    public void updateWithNewValues(Double newWeight, Double newMuscleMass, Double newBodyFatMass, Physical previousPhysical) {
        this.weightChange = previousPhysical != null ? newWeight - previousPhysical.getWeight() : null;
        this.muscleMassChange = previousPhysical != null ? newMuscleMass - previousPhysical.getMuscleMass() : null;
        this.bodyFatMassChange = previousPhysical != null ? newBodyFatMass - previousPhysical.getBodyFatMass() : null;

        this.weight = newWeight;
        this.muscleMass = newMuscleMass;
        this.bodyFatMass = newBodyFatMass;

        this.bmi = newWeight / ((this.height / 100) * (this.height / 100));
        this.bodyFatPercentage = newBodyFatMass / newWeight * 100;
    }

    public void updateFuturePhysical(Physical previousPhysical) {
        if (previousPhysical != null) {
            this.weightChange = this.weight - previousPhysical.getWeight();
            this.muscleMassChange = this.muscleMass - previousPhysical.getMuscleMass();
            this.bodyFatMassChange = this.bodyFatMass - previousPhysical.getBodyFatMass();
        } else {
            this.weightChange = null;
            this.muscleMassChange = null;
            this.bodyFatMassChange = null;
        }
    }
}

