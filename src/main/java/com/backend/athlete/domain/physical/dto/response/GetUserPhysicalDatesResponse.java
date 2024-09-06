package com.backend.athlete.domain.physical.dto.response;

import com.backend.athlete.domain.physical.domain.Physical;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class GetUserPhysicalDatesResponse {
    private LocalDate date;

    public GetUserPhysicalDatesResponse(LocalDate date) {
        this.date = date;
    }
}
