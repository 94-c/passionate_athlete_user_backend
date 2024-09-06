package com.backend.athlete.domain.workoutNotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRecordNoticeRepository extends JpaRepository<WorkoutRecordNotice, Long> {
}
