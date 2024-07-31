package com.backend.athlete.domain.memberShip.dto.response;


import com.backend.athlete.domain.memberShip.domain.MemberShipHistory;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class GetMemberShipHistoryResponse {
    private Long id;
    private LocalDate createdAt;
    private LocalDate oldExpiryDate;
    private LocalDate newExpiryDate;

    public GetMemberShipHistoryResponse(MemberShipHistory history) {
        this.id = history.getId();
        this.createdAt = history.getCreatedAt().toLocalDate();
        this.oldExpiryDate = history.getOldExpiryDate();
        this.newExpiryDate = history.getNewExpiryDate();
    }

    public static GetMemberShipHistoryResponse fromEntity(MemberShipHistory history) {
        return new GetMemberShipHistoryResponse(history);
    }
}
