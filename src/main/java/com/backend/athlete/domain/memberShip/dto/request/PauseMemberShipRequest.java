package com.backend.athlete.domain.memberShip.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class PauseMemberShipRequest {
    private LocalDate pauseStartDate;
    private int pauseDays;

    public PauseMemberShipRequest(LocalDate pauseStartDate, int pauseDays) {
        this.pauseStartDate = pauseStartDate;
        this.pauseDays = pauseDays;
    }

}

