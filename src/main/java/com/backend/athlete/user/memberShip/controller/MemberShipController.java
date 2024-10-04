package com.backend.athlete.user.memberShip.controller;

import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.memberShip.application.MemberShipService;
import com.backend.athlete.user.memberShip.dto.request.CreateMemberShipRequest;
import com.backend.athlete.user.memberShip.dto.request.PauseMemberShipRequest;
import com.backend.athlete.user.memberShip.dto.request.RenewMemberShipRequest;
import com.backend.athlete.user.memberShip.dto.response.CreateMemberShipResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipHistoryResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipPauseResponse;
import com.backend.athlete.user.memberShip.dto.response.GetMemberShipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
public class MemberShipController {
    private final MemberShipService memberShipService;

    // 어드민이 회원에게 회원권 발급
    @PostMapping("/admin")
    public ResponseEntity<CreateMemberShipResponse> createMembership(@RequestBody CreateMemberShipRequest request) {
        CreateMemberShipResponse response = memberShipService.createMembership(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 현재 활성화된 회원권 정보 조회
    @GetMapping("/current")
    public ResponseEntity<GetMemberShipResponse> getCurrentMembership(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetMemberShipResponse response = memberShipService.getCurrentMembership(userPrincipal);
        return ResponseEntity.ok(response);
    }

    // 회원이 회원권 갱신 요청
    @PostMapping("/renew")
    public ResponseEntity<CreateMemberShipResponse> renewMembership(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                                    @RequestBody RenewMemberShipRequest request) {
        CreateMemberShipResponse response = memberShipService.renewMembership(userPrincipal, request.getPeriodType());
        return ResponseEntity.ok(response);
    }

    // 회원의 회원권 히스토리 조회
    @GetMapping("/history")
    public ResponseEntity<List<GetMemberShipHistoryResponse>> getMembershipHistory(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        List<GetMemberShipHistoryResponse> history = memberShipService.getMembershipHistory(userPrincipal);
        return ResponseEntity.ok(history);
    }

    // 회원의 회원권 정지 요청
    @PostMapping("/pause")
    public ResponseEntity<Void> pauseMembership(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                @RequestBody PauseMemberShipRequest request) {
        memberShipService.pauseMembership(userPrincipal, request.getPauseStartDate(), request.getPauseDays());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원의 회원권 정지 히스토리 조회
    @GetMapping("/{memberShipId}/pause/history")
    public ResponseEntity<List<GetMemberShipPauseResponse>> getMembershipPauseHistory(@PathVariable Long memberShipId) {
        List<GetMemberShipPauseResponse> pauseHistory = memberShipService.getMembershipPauseHistory(memberShipId);
        return ResponseEntity.ok(pauseHistory);
    }


}
