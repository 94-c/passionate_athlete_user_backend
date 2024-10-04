package com.backend.athlete.user.memberShip.dto.response;

import com.backend.athlete.user.memberShip.domain.MemberShipPause;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetMemberShipPauseResponse {
    private Long id;
    private Long memberShipId;
    private LocalDate pauseStartDate;
    private LocalDate pauseEndDate;

    public GetMemberShipPauseResponse(MemberShipPause history) {
        this.id = history.getId();
        this.memberShipId = history.getId();
        this.pauseStartDate = history.getPauseStartDate();
        this.pauseEndDate = history.getPauseEndDate();
    }

    public static GetMemberShipPauseResponse fromEntity(MemberShipPause history) {
        return new GetMemberShipPauseResponse(history);
    }

}
