package com.backend.athlete.user.notice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeTypeRepository extends JpaRepository<NoticeType, Long> {
    List<NoticeType> findByRole(String role);

}
