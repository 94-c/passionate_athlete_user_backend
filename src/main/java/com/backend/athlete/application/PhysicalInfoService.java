package com.backend.athlete.application;

import com.backend.athlete.domain.physical.PhysicalInfo;
import com.backend.athlete.domain.physical.PhysicalInfoRepository;
import com.backend.athlete.presentation.physicalInfo.response.GetPhysicalInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhysicalInfoService {

    private final PhysicalInfoRepository physicalInfoRepository;

    public PhysicalInfoService(PhysicalInfoRepository physicalInfoRepository) {
        this.physicalInfoRepository = physicalInfoRepository;
    }

    public List<GetPhysicalInfoResponse> getPhysicalInfo() {
        List<PhysicalInfo> physicalInfoList = physicalInfoRepository.findAll();
        return physicalInfoList.stream()
                .map(GetPhysicalInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
