package com.backend.athlete.domain.memberShip.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemberShipPauseRepository extends JpaRepository<MemberShipPause, Long> {
    List<MemberShipPause> findByMemberShipAndPauseStartDateBetween(MemberShip memberShip, LocalDate localDate, LocalDate localDate1);
    List<MemberShipPause> findByMemberShip_User_Id(Long userId);
}
