package com.backend.athlete.user.physical.infrastructure;

import com.backend.athlete.user.physical.domain.Physical;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalRepositoryCustom {
    List<Physical> findPhysicalRankings(LocalDate startDate, LocalDate endDate, String gender, int limit);
}
