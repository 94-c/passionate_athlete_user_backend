package com.backend.athlete.domain.announcement.repository;

import com.backend.athlete.domain.announcement.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a WHERE a.status = true AND (a.title LIKE %:title% OR a.user.name LIKE %:name%)")
    Page<Announcement> findAllByTitleContainingAndUserNameContainingAndStatusTrue(@Param("title") String title, @Param("name") String name, Pageable pageable);

}
