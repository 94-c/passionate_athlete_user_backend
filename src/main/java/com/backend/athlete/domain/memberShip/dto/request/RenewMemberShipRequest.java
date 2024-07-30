package com.backend.athlete.domain.memberShip.dto.request;

import com.backend.athlete.domain.memberShip.domain.type.PeriodType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RenewMemberShipRequest {
    @NotNull(message = "기간 유형을 선택 해주세요.")
    private PeriodType periodType;

    public RenewMemberShipRequest(PeriodType periodType) {
        this.periodType = periodType;
    }


}
