package com.backend.athlete.application;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.physical.PhysicalRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.physical.request.CreatePhysicalRequest;
import com.backend.athlete.presentation.physical.response.*;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FileUtils;
import com.backend.athlete.support.util.FindUtils;
import com.backend.athlete.support.util.MathUtils;
import com.backend.athlete.support.util.PhysicalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhysicalService {

    private final PhysicalRepository physicalRepository;
    private final UserRepository userRepository;

    public PhysicalService(PhysicalRepository physicalRepository, UserRepository userRepository) {
        this.physicalRepository = physicalRepository;
        this.userRepository = userRepository;
    }

    public CreatePhysicalResponse savePhysical(CustomUserDetailsImpl userPrincipal, CreatePhysicalRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        request.setMeasureDate(LocalDateTime.now());

        boolean existsSave = physicalRepository.existsByUserAndMeasureDate(user, today);
        if (existsSave) {
            throw new ServiceException("하루에 한번만 입력 하실 수 있습니다.");
        }

        Physical previousPhysical = physicalRepository.findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(user, todayStart);

        double bmi = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMI(request.getWeight(), request.getHeight()));
        request.setBmi(bmi);

        double bodyFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBodyFatPercentage(request.getBodyFatMass(), request.getWeight()));
        request.setBodyFatPercentage(bodyFatPercentage);

        double visceralFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateVisceralFatPercentage(bodyFatPercentage));
        request.setVisceralFatPercentage(visceralFatPercentage);

        double bmr = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMR(request.getWeight(), request.getHeight(), 30, user.getGender().toString()));
        request.setBmr(bmr);

        double weightChange = previousPhysical != null ? request.getWeight() - previousPhysical.getWeight() : 0.0;
        double heightChange = previousPhysical != null ? request.getHeight() - previousPhysical.getHeight() : 0.0;
        double muscleMassChange = previousPhysical != null ? request.getMuscleMass() - previousPhysical.getMuscleMass() : 0.0;
        double bodyFatMassChange = previousPhysical != null ? request.getBodyFatMass() - previousPhysical.getBodyFatMass() : 0.0;

        request.setWeightChange(weightChange);
        request.setHeightChange(heightChange);
        request.setMuscleMassChange(muscleMassChange);
        request.setBodyFatMassChange(bodyFatMassChange);

        Physical savePhysical = CreatePhysicalRequest.toEntity(request, user);

        savePhysical = physicalRepository.save(savePhysical);

        if (!Objects.equals(user.getHeight(), request.getHeight()) || !Objects.equals(user.getWeight(), request.getWeight())) {
            user.updatePhysicalAttributes(request.getWeight(), request.getHeight());
            userRepository.save(user);
        }

        return CreatePhysicalResponse.fromEntity(savePhysical);
    }

    @Transactional
    public GetPhysicalResponse getPhysical(CustomUserDetailsImpl userPrincipal, LocalDate dailyDate) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<Physical> physicals = physicalRepository.findPhysicalsByUserIdAndMeasureDate(user.getId(), dailyDate);

        if (physicals.isEmpty()) {
            throw new ServiceException(dailyDate + " 의 인바디 정보가 존재하지 않습니다.");
        }

        Physical physical = physicals.get(0);

        Physical previousPhysical = physicalRepository.findTopByUserAndMeasureDateBeforeOrderByMeasureDateDesc(user, dailyDate.atStartOfDay());

        return GetPhysicalResponse.fromEntity(physical, previousPhysical);
    }

    @Transactional
    public Page<PagePhysicalResponse> getPhysicalData(CustomUserDetailsImpl userPrincipal, int page, int size) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Pageable pageable = PageRequest.of(page, size);
        Page<Physical> physicalPage = physicalRepository.findByUserIdOrderByMeasureDateDesc(user.getId(), pageable);

        List<PagePhysicalResponse> responses = physicalPage.stream()
                .map(PagePhysicalResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, physicalPage.getTotalElements());
    }

    public LastGetPhysicalResponse findLastPhysical() {
        Physical lastPhysical = physicalRepository.findTopByOrderByMeasureDateDesc();
        return LastGetPhysicalResponse.fromEntity(lastPhysical);
    }

    public List<GetPhysicalRankingResponse> getRankingPhysical(String type, String gender, int limit) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        if ("weekly".equalsIgnoreCase(type)) {
            startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            endDate = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        } else if ("monthly".equalsIgnoreCase(type)) {
            startDate = now.with(TemporalAdjusters.firstDayOfMonth());
            endDate = now.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            throw new ServiceException("Invalid type: " + type);
        }

        List<Physical> rankingPhysical = physicalRepository.findPhysicalRankings(startDate, endDate, gender, limit);
        return rankingPhysical.stream()
                .map(physical -> GetPhysicalRankingResponse.fromEntity(physical, startDate, endDate))
                .collect(Collectors.toList());
    }

    public MonthlyFatChangeResponse calculateMonthlyFatChange(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate oneMonthAgo = now.minusMonths(1);

        List<Physical> physicals = physicalRepository.findByUserIdAndMeasureDateBetween(userId, oneMonthAgo.atStartOfDay(), now.atTime(LocalTime.MAX));

        double initialFatMass = physicals.stream()
                .min(Comparator.comparing(Physical::getMeasureDate))
                .map(Physical::getBodyFatMass)
                .orElse(0.0);

        double finalFatMass = physicals.stream()
                .max(Comparator.comparing(Physical::getMeasureDate))
                .map(Physical::getBodyFatMass)
                .orElse(0.0);

        double fatChange = initialFatMass - finalFatMass;

        return MonthlyFatChangeResponse.fromEntity(fatChange);
    }
}
