package com.backend.athlete.domain.notice.application;

import com.backend.athlete.domain.notice.domain.NoticeType;
import com.backend.athlete.domain.notice.domain.NoticeTypeRepository;
import com.backend.athlete.domain.notice.dto.response.GetNoticeTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeTypeService {
    private final NoticeTypeRepository noticeTypeRepository;

    public List<GetNoticeTypeResponse> getNoticeTypes() {
        List<NoticeType> noticeTypes = noticeTypeRepository.findAll();
        return noticeTypes.stream()
                .map(GetNoticeTypeResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public List<GetNoticeTypeResponse> getNoticeTypesByRole(String role) {
        List<NoticeType> noticeTypes = noticeTypeRepository.findByRole(role);
        return noticeTypes.stream()
                .map(GetNoticeTypeResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
