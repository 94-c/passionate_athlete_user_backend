package com.backend.athlete.user.physical.application;

import com.backend.athlete.user.physical.domain.PhysicalInfo;
import com.backend.athlete.user.physical.domain.PhysicalInfoRepository;
import com.backend.athlete.user.physical.dto.response.GetPhysicalInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhysicalInfoService {
    private final PhysicalInfoRepository physicalInfoRepository;

    public List<GetPhysicalInfoResponse> getPhysicalInfo() {
        List<PhysicalInfo> physicalInfoList = physicalInfoRepository.findAll();
        return physicalInfoList.stream()
                .map(GetPhysicalInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
