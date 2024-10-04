package com.backend.athlete.user.physical.dto.response;

import com.backend.athlete.user.physical.domain.Physical;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetMonthlyFatChangeResponse {
    private final List<UserRankingInfo> rankingList;

    public GetMonthlyFatChangeResponse(List<UserRankingInfo> rankingList) {
        this.rankingList = rankingList;
    }

    public static GetMonthlyFatChangeResponse fromEntityList(List<Physical> physicalList) {
        List<UserRankingInfo> rankingList = physicalList.stream()
                .map(physical -> new UserRankingInfo(
                        physical.getUser().getName(),
                        physical.getUser().getBranch().getName(),
                        physical.getBodyFatMassChange()
                ))
                .collect(Collectors.toList());

        return new GetMonthlyFatChangeResponse(rankingList);
    }

    @Getter
    public static class UserRankingInfo {
        private final String username;
        private final String branchName;
        private final double bodyFatMassChange;

        public UserRankingInfo(String username, String branchName, double bodyFatMassChange) {
            this.username = username;
            this.branchName = branchName;
            this.bodyFatMassChange = bodyFatMassChange;
        }
    }
}
