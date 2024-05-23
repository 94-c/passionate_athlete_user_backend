package com.backend.athlete.domain.physical.service;

import com.backend.athlete.domain.physical.dto.request.SavePhysicalRequest;
import com.backend.athlete.domain.physical.dto.response.SavePhysicalUserResponse;
import com.backend.athlete.domain.physical.model.Physical;
import com.backend.athlete.domain.physical.repository.PhysicalRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.global.util.MathUtils;
import com.backend.athlete.global.util.PhysicalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * 인바디 저장
     *  TODO : 인바디 수치 재측정해야함, 추가적으로 소수점 둘째짜리까지만 표기
     *        일별로 한번만 등록 할 수 있도록 변경
     */

    public SavePhysicalUserResponse savePhysical(CustomUserDetailsImpl userPrincipal, SavePhysicalRequest dto) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        LocalDate today = LocalDate.now();
        dto.setMeasureDate(today);

        boolean existsSave = physicalRepository.existsByUserAndMeasureDate(findUser, today);
        if (existsSave) {
            throw new IllegalArgumentException("You can only register once per day.");
        }

        double bmi = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMI(findUser.getWeight(), dto.getHeight()));
        dto.setBmi(bmi);

        double bodyFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBodyFatPercentage(dto.getBodyFatMass(), dto.getWeight()));
        dto.setBodyFatPercentage(bodyFatPercentage);

        double visceralFatPercentage = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateVisceralFatPercentage(bodyFatPercentage));
        dto.setVisceralFatPercentage(visceralFatPercentage);

        double bmr = MathUtils.roundToTwoDecimalPlaces(PhysicalUtils.calculateBMR(dto.getWeight(), dto.getHeight(), 30, findUser.getGender().toString()));
        dto.setBmr(bmr);

        Physical savePhysical = physicalRepository.save(SavePhysicalRequest.toEntity(dto, findUser));

        if (!Objects.equals(findUser.getHeight(), dto.getHeight()) || !Objects.equals(findUser.getWeight(), dto.getWeight())) {
            findUser.updatePhysicalAttributes(dto.getWeight(), dto.getHeight());
            userRepository.save(findUser);
        }

        return SavePhysicalUserResponse.fromEntity(savePhysical);
    }



    /**
     * 인바디 전체 조회 (최신 날짜 순, 페이징처리)
     */

    /**
     * TODO 삭제는 추후 생각중
     */

}
