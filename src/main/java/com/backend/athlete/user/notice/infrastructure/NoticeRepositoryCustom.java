package com.backend.athlete.user.notice.infrastructure;

import com.backend.athlete.user.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> findAllByUserAndTitleAndKindAndStatus(String name, String title, Pageable pageable, Long kindId, boolean status);
    Page<Notice> findAllByKindId(Long kindId, Pageable pageable, boolean status);
}
