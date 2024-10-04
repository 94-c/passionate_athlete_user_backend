package com.backend.athlete.user.memberShip.dto.response;

import com.backend.athlete.user.memberShip.domain.MemberShip;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetMemberShipResponse {
    private Long id;
    private String username;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private boolean status;

    public GetMemberShipResponse(MemberShip memberShip) {
        this.id = memberShip.getId();
        this.username = memberShip.getUser().getName();
        this.startDate = memberShip.getStartDate();
        this.expiryDate = memberShip.getExpiryDate();
        this.status = memberShip.isStatus();
    }
    public static GetMemberShipResponse fromEntity(MemberShip memberShip) {
        return new GetMemberShipResponse(memberShip);
    }
}
