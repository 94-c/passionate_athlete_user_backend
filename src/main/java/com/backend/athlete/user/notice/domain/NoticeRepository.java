package com.backend.athlete.user.notice.domain;

import com.backend.athlete.user.notice.infrastructure.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {

}
