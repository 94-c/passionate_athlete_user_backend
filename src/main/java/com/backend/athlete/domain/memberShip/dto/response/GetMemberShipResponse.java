package com.backend.athlete.domain.memberShip.dto.response;

import com.backend.athlete.domain.memberShip.domain.MemberShip;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetMemberShipResponse {
    private Long id;
    private String username;
    private LocalDate expiryDate;
    private boolean status;

    public GetMemberShipResponse(MemberShip memberShip) {
        this.id = memberShip.getId();
        this.username = memberShip.getUser().getName();
        this.expiryDate = memberShip.getExpiryDate();
        this.status = memberShip.isStatus();
    }

    public static GetMemberShipResponse fromEntity(MemberShip memberShip) {
        return new GetMemberShipResponse(memberShip);
    }
}
