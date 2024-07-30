package com.backend.athlete.domain.memberShip.application;

import com.backend.athlete.domain.memberShip.domain.MemberShip;
import com.backend.athlete.domain.memberShip.domain.MemberShipRepository;
import com.backend.athlete.domain.memberShip.domain.type.PeriodType;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberShipService {

    private final MemberShipRepository memberShipRepository;
    private final UserRepository userRepository;

    @Transactional
    public void issueMembership(Long userId, PeriodType periodType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        LocalDate expiryDate = LocalDate.now().plusMonths(periodType.getMonths());
        MemberShip memberShip = new MemberShip(user, expiryDate, true);
        memberShipRepository.save(memberShip);
    }

    @Transactional
    public void renewMembershipRequest(Long memberId, PeriodType periodType) {
        MemberShip memberShip = memberShipRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        LocalDate newExpiryDate = memberShip.getExpiryDate().plusMonths(periodType.getMonths());
        memberShip.renewMembership(newExpiryDate);
        memberShipRepository.save(memberShip);
    }

    @Transactional
    public void pauseMembership(Long memberId, int days) {
        MemberShip memberShip = memberShipRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        memberShip.pauseMembership(days);
        memberShipRepository.save(memberShip);
    }

}
