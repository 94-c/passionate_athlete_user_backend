package com.backend.athlete.domain.memberShip.dto.request;

import com.backend.athlete.domain.memberShip.domain.MemberShip;
import com.backend.athlete.domain.memberShip.domain.type.PeriodType;
import com.backend.athlete.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RenewMemberShipRequest {
    @NotNull(message = "기간 유형을 선택 해주세요.")
    private PeriodType periodType;

    public static MemberShip toEntity(RenewMemberShipRequest request, User user) {
        LocalDate startDate = LocalDate.now();
        LocalDate expiryDate = startDate.plusMonths(request.getPeriodType().getMonths());
        return new MemberShip(user, startDate, expiryDate, true);
    }
}
