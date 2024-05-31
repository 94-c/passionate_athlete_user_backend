package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
