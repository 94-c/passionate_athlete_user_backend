package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> findAllByUserAndTitleAndKindAndStatus(String name, String title, Pageable pageable, NoticeType kind, boolean status);
}
