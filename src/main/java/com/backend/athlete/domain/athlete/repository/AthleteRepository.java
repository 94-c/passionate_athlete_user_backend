package com.backend.athlete.domain.athlete.repository;

import com.backend.athlete.domain.athlete.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

}
