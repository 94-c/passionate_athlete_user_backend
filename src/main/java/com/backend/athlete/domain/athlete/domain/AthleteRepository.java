package com.backend.athlete.domain.athlete.domain;

import com.backend.athlete.domain.athlete.infrastructure.AthleteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long>, AthleteRepositoryCustom {

}
