package com.backend.athlete.domain.memberShip.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_ships")
public class MemberShip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원권 인덱스")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Comment("회원권 시작 날짜")
    private LocalDate startDate;

    @Comment("회원권 만료 날짜")
    private LocalDate expiryDate;

    @OneToMany(mappedBy = "memberShip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberShipHistory> histories = new ArrayList<>();

    @Comment("회원권 활성화")
    private boolean status;

    @Comment("정지 일수")
    private int pausedDays;

    public MemberShip(User user, LocalDate startDate, LocalDate expiryDate, boolean status) {
        this.user = user;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.pausedDays = 0;
    }

    public void renewMembership(LocalDate newExpiryDate) {
        MemberShipHistory history = new MemberShipHistory(this, this.expiryDate, newExpiryDate);
        histories.add(history);
        this.expiryDate = newExpiryDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void pauseMembership(int days) {
        if (days > 5) {
            throw new IllegalArgumentException("최대 5일까지만 정지할 수 있습니다.");
        }
        this.pausedDays += days;
    }

}
