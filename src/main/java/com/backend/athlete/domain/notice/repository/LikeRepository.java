package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
