package com.backend.athlete.domain.memberShip.domain.type;

import lombok.Getter;

@Getter
public enum PeriodType {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    ONE_YEAR(12);

    private final int months;

    PeriodType(int months) {
        this.months = months;
    }

}
