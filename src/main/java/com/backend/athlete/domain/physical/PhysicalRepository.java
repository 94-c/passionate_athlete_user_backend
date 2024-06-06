package com.backend.athlete.domain.physical;

import com.backend.athlete.domain.user.User;
import com.backend.athlete.infrastructure.PhysicalRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom {
    boolean existsByUserAndMeasureDate(User user, LocalDate measureDate);
    Page<Physical> findByUserIdOrderByMeasureDateDesc(Long id, Pageable pageable);

}
