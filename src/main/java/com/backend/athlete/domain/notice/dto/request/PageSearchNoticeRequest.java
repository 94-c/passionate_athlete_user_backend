package com.backend.athlete.domain.notice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageSearchNoticeRequest {

    private String title;
    private String name;

    public PageSearchNoticeRequest(String title, String name) {
        this.title = title;
        this.name = name;
    }
}
