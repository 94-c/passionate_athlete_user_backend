package com.backend.athlete.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageSearchBranchRequest {

    private String name;
    private String managerName;

    public PageSearchBranchRequest(String name, String managerName) {
        this.name = name;
        this.managerName = managerName;
    }

}