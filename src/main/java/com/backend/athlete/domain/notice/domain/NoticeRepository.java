package com.backend.athlete.domain.notice.domain;

import com.backend.athlete.domain.notice.infrastructure.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {

}
