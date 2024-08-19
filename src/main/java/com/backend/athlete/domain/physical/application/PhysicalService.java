package com.backend.athlete.domain.physical.application;

import com.backend.athlete.domain.physical.domain.Physical;
import com.backend.athlete.domain.physical.domain.PhysicalRepository;
import com.backend.athlete.domain.physical.dto.request.UpdatePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.*;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.domain.physical.dto.request.CreatePhysicalRequest;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import com.backend.athlete.support.util.MathUtils;
import com.backend.athlete.support.util.PhysicalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhysicalService {
    private final PhysicalRepository physicalRepository;
    private final UserRepository userRepository;


    public CreatePhysicalResponse savePhysical(CustomUserDetailsImpl userPrincipal, CreatePhysicalRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        request.setMeasureDate(LocalDateTime.now());

        boolean existsSave = physicalRepository.existsByUserAndMeasureDate(user, today);
        if (existsSave) {
            throw new NotFoundException("하루에 한번만 입력 하실 수 있습니다." , HttpStatus.NOT_FOUND);
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
            throw new NotFoundException(dailyDate + " 의 인바디 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
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

    @Transactional
    public void updatePhysical(Long physicalId, UpdatePhysicalRequest updateRequest) {
        Physical physical = physicalRepository.findById(physicalId)
                .orElseThrow(() -> new NotFoundException("Physical data not found", HttpStatus.NOT_FOUND));

        Physical previousPhysical = physicalRepository.findTopByUserAndMeasureDateBeforeOrderByMeasureDateDesc(
                physical.getUser(), physical.getMeasureDate());

        updateRequest.fromEntity(physical, previousPhysical);

        physicalRepository.save(physical);
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
            throw new NotFoundException("Invalid type: " + type, HttpStatus.NOT_FOUND);
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
