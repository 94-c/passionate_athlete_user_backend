package com.backend.athlete.application;

import com.backend.athlete.domain.notice.NoticeType;
import com.backend.athlete.domain.notice.NoticeTypeRepository;
import com.backend.athlete.presentation.notice.response.GetNoticeTypeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeTypeService {
    private final NoticeTypeRepository noticeTypeRepository;

    public NoticeTypeService(NoticeTypeRepository noticeTypeRepository) {
        this.noticeTypeRepository = noticeTypeRepository;
    }

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
