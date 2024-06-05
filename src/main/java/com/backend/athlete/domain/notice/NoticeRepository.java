package com.backend.athlete.domain.notice;

import com.backend.athlete.infrastructure.NoticeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {

}
