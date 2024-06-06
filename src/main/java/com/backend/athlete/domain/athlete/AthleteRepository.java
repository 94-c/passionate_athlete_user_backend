package com.backend.athlete.domain.athlete;

import com.backend.athlete.domain.athlete.data.AthleteData;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.infrastructure.AthleteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long>, AthleteRepositoryCustom {

}
