package com.backend.athlete.user.physical.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetUserPhysicalDatesResponse {
    private LocalDate date;

    public GetUserPhysicalDatesResponse(LocalDate date) {
        this.date = date;
    }
}
