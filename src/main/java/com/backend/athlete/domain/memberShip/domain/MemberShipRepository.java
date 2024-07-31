package com.backend.athlete.domain.memberShip.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {
    Optional<MemberShip> findByUserId(Long userId);
    Optional<MemberShip> findByUserIdAndStatusTrue(Long userId);
}
