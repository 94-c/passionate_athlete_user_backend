package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
