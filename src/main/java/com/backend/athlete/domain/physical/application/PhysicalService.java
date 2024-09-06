package com.backend.athlete.domain.physical.application;

import com.backend.athlete.domain.physical.domain.Physical;
import com.backend.athlete.domain.physical.domain.PhysicalRepository;
import com.backend.athlete.domain.physical.dto.request.UpdatePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.*;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.domain.physical.dto.request.CreatePhysicalRequest;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import com.backend.athlete.support.util.MathUtils;
import com.backend.athlete.support.util.PhysicalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhysicalService {
    private final PhysicalRepository physicalRepository;
    private final UserRepository userRepository;


    public CreatePhysicalResponse savePhysical(CustomUserDetailsImpl userPrincipal, CreatePhysicalRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        LocalDateTime measureDate = request.getMeasureDate(); // 사용자가 입력한 측정 날짜를 사용합니다.
        LocalDate measureDateOnly = measureDate.toLocalDate(); // 날짜 부분만 추출
        LocalDateTime measureDateStart = measureDateOnly.atStartOfDay(); // 자정 시작 시간

        // 사용자가 입력한 날짜로 데이터를 저장합니다.
        request.setMeasureDate(measureDate);

        boolean existsSave = physicalRepository.existsByUserAndMeasureDate(user, measureDateOnly);
        if (existsSave) {
            throw new NotFoundException("하루에 한번만 입력 하실 수 있습니다.", HttpStatus.NOT_FOUND);
        }

        // 입력된 날짜 이전의 가장 최신 데이터를 가져옵니다.
        Physical previousPhysical = physicalRepository.findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(user, measureDateStart);

        // 측정 데이터를 계산합니다.
        double bmi = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMI(request.getWeight(), request.getHeight()));
        request.setBmi(bmi);

        double bodyFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBodyFatPercentage(request.getBodyFatMass(), request.getWeight()));
        request.setBodyFatPercentage(bodyFatPercentage);

        double visceralFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateVisceralFatPercentage(bodyFatPercentage));
        request.setVisceralFatPercentage(visceralFatPercentage);

        double bmr = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMR(request.getWeight(), request.getHeight(), 30, user.getGender().toString()));
        request.setBmr(bmr);

        // 이전 데이터와 비교하여 변화량을 계산합니다.
        double weightChange = previousPhysical != null ? request.getWeight() - previousPhysical.getWeight() : 0.0;
        double heightChange = previousPhysical != null ? request.getHeight() - previousPhysical.getHeight() : 0.0;
        double muscleMassChange = previousPhysical != null ? request.getMuscleMass() - previousPhysical.getMuscleMass() : 0.0;
        double bodyFatMassChange = previousPhysical != null ? request.getBodyFatMass() - previousPhysical.getBodyFatMass() : 0.0;

        request.setWeightChange(weightChange);
        request.setHeightChange(heightChange);
        request.setMuscleMassChange(muscleMassChange);
        request.setBodyFatMassChange(bodyFatMassChange);

        Physical savePhysical = CreatePhysicalRequest.toEntity(request, user);

        // 새로운 데이터를 저장합니다.
        savePhysical = physicalRepository.save(savePhysical);

        // 사용자의 신체 정보 업데이트 (만약 현재의 신체 정보와 다를 경우)
        if (!Objects.equals(user.getHeight(), request.getHeight()) || !Objects.equals(user.getWeight(), request.getWeight())) {
            user.updatePhysicalAttributes(request.getWeight(), request.getHeight());
            userRepository.save(user);
        }

        // 사용자가 입력한 날짜 이후에 기록된 최신 데이터를 찾아 변화량을 업데이트합니다.
        updateFuturePhysicalRecords(user, measureDate);

        return CreatePhysicalResponse.fromEntity(savePhysical);
    }

    private void updateFuturePhysicalRecords(User user, LocalDateTime measureDate) {
        List<Physical> futurePhysicals = physicalRepository.findByUserAndMeasureDateAfterOrderByMeasureDateAsc(user, measureDate);

        Physical previousPhysical = physicalRepository.findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(user, measureDate);

        for (Physical futurePhysical : futurePhysicals) {
            futurePhysical.updateFuturePhysical(previousPhysical);
            previousPhysical = futurePhysical; // 현재를 이전으로 설정
            physicalRepository.save(futurePhysical); // 업데이트된 데이터를 저장
        }
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

    public LastGetPhysicalResponse findLastPhysical(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        Physical lastPhysical = physicalRepository.findTopByUserIdOrderByMeasureDateDesc(user.getId());
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

    public MonthlyFatChangeResponse calculateMonthlyFatChange(CustomUserDetailsImpl userPrincipal, int year, int month) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        Physical fatChange = physicalRepository.findPhysicalByBodyFatMassChangeInDateRange(user.getId(), startDateTime, endDateTime);

        if (fatChange == null) {
            return new MonthlyFatChangeResponse(0.0);
        }

        return MonthlyFatChangeResponse.fromEntity(fatChange);
    }

    public GetMonthlyFatChangeResponse getMonthlyFatChangeRanking(int year, int month, Long branchId, UserGenderType gender, int limit) {
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        List<Physical> physicals;
        if (branchId == null || branchId.equals(0L)) {
            physicals = physicalRepository.findTopRankingsByDateRange(gender, startDateTime, endDateTime, PageRequest.of(0, limit));
        } else {
            physicals = physicalRepository.findTopRankingsByBranchAndDateRange(branchId, gender, startDateTime, endDateTime, PageRequest.of(0, limit));
        }
        return GetMonthlyFatChangeResponse.fromEntityList(physicals);
    }

    public Page<GetPhysicalHistoryResponse> getPhysicalHistoryByType(CustomUserDetailsImpl userPrincipal, String type, int page, int size) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "measureDate"));

        return physicalRepository.findByUserId(user.getId(), pageable)
                .map(physical -> GetPhysicalHistoryResponse.fromEntity(physical, type));
    }

    public List<GetUserPhysicalDatesResponse> getUserPhysicalDates(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        List<LocalDateTime> dateTimes = physicalRepository.findMeasureDatesByUser(user);

        return dateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .map(GetUserPhysicalDatesResponse::new)
                .collect(Collectors.toList());
    }


}
