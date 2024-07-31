package com.backend.athlete.domain.memberShip.application;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.memberShip.domain.*;
import com.backend.athlete.domain.memberShip.domain.type.PeriodType;
import com.backend.athlete.domain.memberShip.dto.request.CreateMemberShipRequest;
import com.backend.athlete.domain.memberShip.dto.response.CreateMemberShipResponse;
import com.backend.athlete.domain.memberShip.dto.response.GetMemberShipHistoryResponse;
import com.backend.athlete.domain.memberShip.dto.response.GetMemberShipPauseResponse;
import com.backend.athlete.domain.memberShip.dto.response.GetMemberShipResponse;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        LocalDate oldExpiryDate = memberShip.getExpiryDate();
        LocalDate newExpiryDate = oldExpiryDate.plusMonths(periodType.getMonths());

        MemberShipHistory memberShipHistory = new MemberShipHistory(memberShip, oldExpiryDate, newExpiryDate);
        memberShipHistoryRepository.save(memberShipHistory);

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

        List<MemberShipPause> pauses = memberShipPauseRepository.findByMemberShipAndPauseStartDateBetween(
                memberShip, pauseStartDate.withDayOfMonth(1), pauseStartDate.withDayOfMonth(pauseStartDate.lengthOfMonth()));

        if (!pauses.isEmpty()) {
            throw new IllegalArgumentException("Membership can only be paused once per month");
        }

        LocalDate pauseEndDate = pauseStartDate.plusDays(pauseDays - 1);

        MemberShipPause memberShipPause = new MemberShipPause(memberShip, pauseStartDate, pauseEndDate);
        memberShipPauseRepository.save(memberShipPause);

        // Adjust the expiry date
        LocalDate newExpiryDate = memberShip.getExpiryDate().plusDays(pauseDays);
        memberShip.renewMembership(newExpiryDate);
        memberShipRepository.save(memberShip);
    }

    public List<GetMemberShipHistoryResponse> getMembershipHistory(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<MemberShipHistory> historyList = memberShipHistoryRepository.findByMemberShip_User_IdOrderByCreatedAtDesc(user.getId());
        return historyList.stream()
                .map(GetMemberShipHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<GetMemberShipPauseResponse> getMembershipPauseHistory(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<MemberShipPause> pauseList = memberShipPauseRepository.findByMemberShip_User_Id(user.getId());
        return pauseList.stream()
                .map(GetMemberShipPauseResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
