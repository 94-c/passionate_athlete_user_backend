package com.backend.athlete.domain.athlete.domain;

import com.backend.athlete.domain.athlete.domain.type.AthleteSuccessType;
import com.backend.athlete.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "athletes")
public class Athlete {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("운동 기록 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("운동 날짜")
    @CreatedDate
    @Column(name = "daily_time")
    private LocalDate dailyTime;

    @Comment("운동 종목")
    private String athletics;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Comment("운동 성공 여부")
    private AthleteSuccessType type;

    @Comment("운동 시간")
    @Column(name = "record")
    private LocalTime record;

    @Comment("운동 라운드")
    @Column(name = "round")
    private Integer round;

    @Comment("비고")
    private String etc;

    /**
     * TODO : 어드민 프로젝트로 이관 예정
     */
    public Athlete(User user, LocalDate dailyTime, String athletics, AthleteSuccessType type, LocalTime record, Integer round, String etc) {
        this.user = user;
        this.dailyTime = dailyTime;
        this.athletics = athletics;
        this.type = type;
        this.record = record;
        this.round = round;
        this.etc = etc;
    }

}
