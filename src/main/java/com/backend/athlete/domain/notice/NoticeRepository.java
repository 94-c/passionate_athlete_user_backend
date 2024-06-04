package com.backend.athlete.domain.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE " +
            "(:name IS NULL OR :name = '' OR n.user.name LIKE %:name%) AND " +
            "(:title IS NULL OR :title = '' OR n.title LIKE %:title%) " +
            "ORDER BY n.createdDate DESC")
    Page<Notice> findAllByUserAndTitle(String name, String title, Pageable pageable);


}
