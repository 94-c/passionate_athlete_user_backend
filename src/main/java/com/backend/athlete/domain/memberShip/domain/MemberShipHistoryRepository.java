package com.backend.athlete.domain.memberShip.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberShipHistoryRepository extends JpaRepository<MemberShipHistory, Long> {
    List<MemberShipHistory> findByMemberShip_User_IdOrderByCreatedAtDesc(Long userId);

}
