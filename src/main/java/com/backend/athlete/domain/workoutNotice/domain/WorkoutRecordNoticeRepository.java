package com.backend.athlete.domain.workoutNotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRecordNoticeRepository extends JpaRepository<WorkoutRecordNotice, Long> {

}
