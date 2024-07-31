package com.backend.athlete.domain.memberShip.dto.response;

import com.backend.athlete.domain.memberShip.domain.MemberShipPause;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetMemberShipPauseResponse {
    private Long id;
    private LocalDate pauseStartDate;
    private LocalDate pauseEndDate;

    public GetMemberShipPauseResponse(Long id, LocalDate pauseStartDate, LocalDate pauseEndDate) {
        this.id = id;
        this.pauseStartDate = pauseStartDate;
        this.pauseEndDate = pauseEndDate;
    }

    public static GetMemberShipPauseResponse fromEntity(MemberShipPause memberShipPause) {
        return new GetMemberShipPauseResponse(
                memberShipPause.getId(),
                memberShipPause.getPauseStartDate(),
                memberShipPause.getPauseEndDate()
        );
    }
}
