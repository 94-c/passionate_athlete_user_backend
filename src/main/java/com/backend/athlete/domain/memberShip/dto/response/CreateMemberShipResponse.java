package com.backend.athlete.domain.memberShip.dto.response;

import com.backend.athlete.domain.memberShip.domain.MemberShip;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateMemberShipResponse {
    private Long id;
    private String username;
    private LocalDate startDate;
    private LocalDate expiryDate;

    public CreateMemberShipResponse(MemberShip memberShip) {
        this.id = memberShip.getId();
        this.username = memberShip.getUser().getName();
        this.startDate = memberShip.getStartDate();
        this.expiryDate = memberShip.getExpiryDate();
    }

    public static CreateMemberShipResponse fromEntity(MemberShip memberShip) {
        return new CreateMemberShipResponse(memberShip);
    }
}
