package com.backend.athlete.domain.memberShip.application;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.memberShip.domain.MemberShip;
import com.backend.athlete.domain.memberShip.domain.MemberShipHistory;
import com.backend.athlete.domain.memberShip.domain.MemberShipHistoryRepository;
import com.backend.athlete.domain.memberShip.domain.MemberShipRepository;
import com.backend.athlete.domain.memberShip.domain.type.PeriodType;
import com.backend.athlete.domain.memberShip.dto.request.CreateMemberShipRequest;
import com.backend.athlete.domain.memberShip.dto.response.CreateMemberShipResponse;
import com.backend.athlete.domain.memberShip.dto.response.GetMemberShipHistoryResponse;
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

        LocalDate newExpiryDate = memberShip.getExpiryDate().plusMonths(periodType.getMonths());
        memberShip.renewMembership(newExpiryDate);
        memberShipRepository.save(memberShip);
        return CreateMemberShipResponse.fromEntity(memberShip);
    }

    public List<GetMemberShipHistoryResponse> getMembershipHistory(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<MemberShipHistory> historyList = memberShipHistoryRepository.findByMemberShip_User_Id(user.getId());
        return historyList.stream()
                .map(GetMemberShipHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
