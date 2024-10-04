package com.backend.athlete.user.memberShip.domain;

import com.backend.athlete.user.user.domain.User;
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
@Table(name = "member_ship_history")
public class MemberShipHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원권 갱신 히스토리 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Column(name = "member_ship_id", nullable = false)
    @Comment("회원권 아이디")
    private Long memberShipId;

    @Comment("이전 만료 날짜")
    private LocalDate oldExpiryDate;

    @Comment("새로운 만료 날짜")
    private LocalDate newExpiryDate;

    public MemberShipHistory(User user, Long memberShipId, LocalDate oldExpiryDate, LocalDate newExpiryDate) {
        this.user = user;
        this.memberShipId = memberShipId;
        this.oldExpiryDate = oldExpiryDate;
        this.newExpiryDate = newExpiryDate;
    }

}
