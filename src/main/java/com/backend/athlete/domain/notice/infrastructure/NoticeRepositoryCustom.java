package com.backend.athlete.domain.notice.infrastructure;

import com.backend.athlete.domain.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> findAllByUserAndTitleAndKindAndStatus(String name, String title, Pageable pageable, Long kindId, boolean status);
}
