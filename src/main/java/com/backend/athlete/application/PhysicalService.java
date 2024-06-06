package com.backend.athlete.application;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.physical.PhysicalRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.physical.request.CreatePhysicalRequest;
import com.backend.athlete.presentation.physical.response.PagePhysicalResponse;
import com.backend.athlete.presentation.physical.response.GetPhysicalResponse;
import com.backend.athlete.presentation.physical.response.CreatePhysicalResponse;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        request.setMeasureDate(today);

        boolean existsSave = physicalRepository.existsByUserAndMeasureDate(user, today);
        if (existsSave) {
            throw new IllegalArgumentException("하루에 한번만 입력 하실 수 있습니다.");
        }

        double bmi = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMI(user.getWeight(), request.getHeight()));
        request.setBmi(bmi);

        double bodyFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBodyFatPercentage(request.getBodyFatMass(), request.getWeight()));
        request.setBodyFatPercentage(bodyFatPercentage);

        double visceralFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateVisceralFatPercentage(bodyFatPercentage));
        request.setVisceralFatPercentage(visceralFatPercentage);

        double bmr = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMR(request.getWeight(), request.getHeight(), 30, user.getGender().toString()));
        request.setBmr(bmr);

        Physical savePhysical = physicalRepository.save(CreatePhysicalRequest.toEntity(request, user));

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

        return GetPhysicalResponse.fromEntity(physical);
    }

    @Transactional
    public Page<PagePhysicalResponse> getPhysicalData(CustomUserDetailsImpl userPrincipal, int page, int size) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Pageable pageable = PageRequest.of(page, size);
        Page<Physical> physicalPage = physicalRepository.findByUserIdOrderByMeasureDateDesc(user.getId(), pageable);

        List<PagePhysicalResponse> responses = new ArrayList<>();
        Physical previousPhysical = null;

        for (Physical physical : physicalPage) {
            PagePhysicalResponse response = PagePhysicalResponse.fromEntity(physical, previousPhysical);
            previousPhysical = physical;
            responses.add(response);
        }

        return new PageImpl<>(responses, pageable, physicalPage.getTotalElements());
    }



}
