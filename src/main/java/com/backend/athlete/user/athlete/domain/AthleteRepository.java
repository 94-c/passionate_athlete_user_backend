package com.backend.athlete.user.athlete.domain;

import com.backend.athlete.user.athlete.infrastructure.AthleteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long>, AthleteRepositoryCustom {

}
