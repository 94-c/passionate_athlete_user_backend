package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.physical.Physical;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalRepositoryCustom {
    List<Physical> findPhysicalsByUserIdAndMeasureDate(Long userId, LocalDate measureDate);

}
