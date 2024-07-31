package com.backend.athlete.domain.memberShip.domain;

import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_ship_pauses_history")
public class MemberShipPause extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원권 정지 히스토리 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_ship_id", nullable = false)
    @Comment("회원권 아이디")
    private MemberShip memberShip;

    @Comment("정지 시작 날짜")
    private LocalDate pauseStartDate;

    @Comment("정지 종료 날짜")
    private LocalDate pauseEndDate;

    public MemberShipPause(MemberShip memberShip, LocalDate pauseStartDate, LocalDate pauseEndDate) {
        this.memberShip = memberShip;
        this.pauseStartDate = pauseStartDate;
        this.pauseEndDate = pauseEndDate;
    }
}
