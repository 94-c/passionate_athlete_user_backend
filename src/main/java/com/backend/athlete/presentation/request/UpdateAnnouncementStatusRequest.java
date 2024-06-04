package com.backend.athlete.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnnouncementStatusRequest {
    private boolean status;
    public UpdateAnnouncementStatusRequest(boolean status) {
        this.status = status;
    }
}
