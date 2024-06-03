package com.backend.athlete.domain.announcement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageSearchAnnouncementRequest {
    private String title;
    private String name;

    public PageSearchAnnouncementRequest(String title, String name) {
        this.title = title;
        this.name = name;
    }

}