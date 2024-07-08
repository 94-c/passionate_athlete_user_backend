package com.backend.athlete.domain.athlete;

import com.backend.athlete.infrastructure.AthleteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long>, AthleteRepositoryCustom {

}
