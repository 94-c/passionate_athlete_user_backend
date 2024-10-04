package com.backend.athlete.user.memberShip.application;

import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.memberShip.domain.*;
import com.backend.athlete.user.memberShip.domain.type.PeriodType;
import com.backend.athlete.user.memberShip.dto.request.CreateMemberShipRequest;
import com.backend.athlete.user.memberShip.dto.response.CreateMemberShipResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipHistoryResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipPauseResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipResponse;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.UserRepository;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberShipService {
    private final MemberShipRepository memberShipRepository;
    private final UserRepository userRepository;
    private final MemberShipHistoryRepository memberShipHistoryRepository;
    private final MemberShipPauseRepository memberShipPauseRepository;

    public GetMemberShipResponse getCurrentMembership(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        MemberShip memberShip = memberShipRepository.findByUserIdAndStatusTrue(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("No active membership found for user"));

        return GetMemberShipResponse.fromEntity(memberShip);
    }

    @Transactional
    public CreateMemberShipResponse createMembership(CreateMemberShipRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        MemberShip memberShip = CreateMemberShipRequest.toEntity(request, user);
        memberShipRepository.save(memberShip);
        return CreateMemberShipResponse.fromEntity(memberShip);
    }

    @Transactional
    public CreateMemberShipResponse renewMembership(CustomUserDetailsImpl userPrincipal, PeriodType periodType) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        MemberShip memberShip = memberShipRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("No active membership found for user"));

        LocalDate today = LocalDate.now();
        if (!memberShip.getExpiryDate().isBefore(today.plusDays(5))) {
            throw new IllegalStateException("Membership can only be renewed within 5 days of expiry.");
        }

        LocalDate newExpiryDate = memberShip.getExpiryDate().plusMonths(periodType.getMonths());

        MemberShipHistory memberShipHistory = new MemberShipHistory(user, memberShip.getId(), memberShip.getExpiryDate(), newExpiryDate);
        memberShipHistoryRepository.save(memberShipHistory);

        List<MemberShipPause> pauses = memberShipPauseRepository.findByMemberShipId(memberShip.getId());
        for (MemberShipPause pause : pauses) {
            pause.setMemberShipHistoryId(memberShipHistory.getId());
            memberShipPauseRepository.save(pause);
        }

        memberShip.renewMembership(newExpiryDate);
        memberShipRepository.save(memberShip);

        return CreateMemberShipResponse.fromEntity(memberShip);
    }

    @Transactional
    public void pauseMembership(CustomUserDetailsImpl userPrincipal, LocalDate pauseStartDate, int pauseDays) {
        if (pauseDays < 1 || pauseDays > 5) {
            throw new IllegalArgumentException("Pause days must be between 1 and 5");
        }

        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        MemberShip memberShip = memberShipRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("No active membership found for user"));

        LocalDate firstDayOfMonth = pauseStartDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = pauseStartDate.withDayOfMonth(pauseStartDate.lengthOfMonth());

        List<MemberShipPause> pauses = memberShipPauseRepository.findByMemberShipIdAndPauseStartDateBetween(
                memberShip.getId(), firstDayOfMonth, lastDayOfMonth);

        if (!pauses.isEmpty()) {
            throw new IllegalArgumentException("Membership can only be paused once per month");
        }

        LocalDate pauseEndDate = pauseStartDate.plusDays(pauseDays - 1);
        MemberShipPause memberShipPause = new MemberShipPause(memberShip.getId(), pauseStartDate, pauseEndDate);
        memberShipPauseRepository.save(memberShipPause);

        LocalDate newExpiryDate = memberShip.getExpiryDate().plusDays(pauseDays);
        memberShip.renewMembership(newExpiryDate);
        memberShipRepository.save(memberShip);
    }

    public List<GetMemberShipHistoryResponse> getMembershipHistory(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<MemberShipHistory> historyList = memberShipHistoryRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return historyList.stream()
                .map(GetMemberShipHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<GetMemberShipPauseResponse> getMembershipPauseHistory(Long memberShipId) {
        List<MemberShipPause> pauses = memberShipPauseRepository.findByMemberShipId(memberShipId);
        return pauses.stream()
                .map(GetMemberShipPauseResponse::new)
                .collect(Collectors.toList());
    }

}
