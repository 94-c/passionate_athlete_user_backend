package com.backend.athlete.domain.physical;

import com.backend.athlete.domain.user.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@Entity
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
    @Column(name = "muscle_mass", nullable = false)
    private Double muscleMass;
    @Comment("체지방량")
    @Column(name = "body_fat_mass", nullable = false)
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
    private LocalDate measureDate;

    protected Physical() {}

    public Physical(User user, Double weight, Double height, Double muscleMass, Double bodyFatMass, LocalDate measureDate, Double bmi, Double bodyFatPercentage, Double visceralFatPercentage, Double bmr) {
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
    }
}
