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
@Table(name = "member_ship_histories")
public class MemberShipHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원권 갱신 히스토리 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_ship_id", nullable = false)
    @Comment("회원권 아이디")
    private MemberShip memberShip;

    @Comment("이전 만료 날짜")
    private LocalDate oldExpiryDate;

    @Comment("새로운 만료 날짜")
    private LocalDate newExpiryDate;

    public MemberShipHistory(MemberShip memberShip, LocalDate oldExpiryDate, LocalDate newExpiryDate) {
        this.memberShip = memberShip;
        this.oldExpiryDate = oldExpiryDate;
        this.newExpiryDate = newExpiryDate;
    }
}
