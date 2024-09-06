package com.backend.athlete.domain.physical.domain;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.physical.infrastructure.PhysicalRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom, PhysicalQueryRepository {
    Physical findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(User user, LocalDateTime date);
    Page<Physical> findByUserIdOrderByMeasureDateDesc(Long id, Pageable pageable);
    List<Physical> findPhysicalsByUserIdAndMeasureDate(Long userId, LocalDate measureDate);
    Physical findTopByUserIdOrderByMeasureDateDesc(Long userId);
    List<Physical> findByUserAndMeasureDateAfterOrderByMeasureDateAsc(User user, LocalDateTime measureDate);
    Page<Physical> findByUserId(Long userId, Pageable pageable);
}
