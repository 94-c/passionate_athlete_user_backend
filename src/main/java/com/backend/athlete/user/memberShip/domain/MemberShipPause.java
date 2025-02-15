package com.backend.athlete.user.memberShip.domain;

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

    @Column(name = "member_ship_id", nullable = false)
    @Comment("회원권 아이디")
    private Long memberShipId;

    @Comment("정지 시작 날짜")
    private LocalDate pauseStartDate;

    @Comment("정지 종료 날짜")
    private LocalDate pauseEndDate;

    public MemberShipPause(Long memberShipId, LocalDate pauseStartDate, LocalDate pauseEndDate) {
        this.memberShipId = memberShipId;
        this.pauseStartDate = pauseStartDate;
        this.pauseEndDate = pauseEndDate;
    }

    public void setMemberShipHistoryId(Long memberShipId) {
        this.memberShipId = memberShipId;
    }
}
