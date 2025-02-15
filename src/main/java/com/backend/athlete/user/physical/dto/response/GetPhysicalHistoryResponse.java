package com.backend.athlete.user.physical.dto.response;

import com.backend.athlete.user.physical.domain.Physical;
import lombok.Getter;
import org.hibernate.service.spi.ServiceException;

import java.time.LocalDateTime;

@Getter
public class GetPhysicalHistoryResponse {
    private LocalDateTime measureDate;
    private Double value;

    public GetPhysicalHistoryResponse(LocalDateTime measureDate, Double value) {
        this.measureDate = measureDate;
        this.value = value;
    }

    public static GetPhysicalHistoryResponse fromEntity(Physical physical, String type) {
        Double value = switch (type.toLowerCase()) {
            case "weight" -> physical.getWeight();
            case "muscle" -> physical.getMuscleMass();
            case "fat" -> physical.getBodyFatMass();
            case "bmi" -> physical.getBmi();
            case "bodyfatpercentage" -> physical.getBodyFatPercentage();
            default -> throw new ServiceException("Invalid type specified.");
        };

        return new GetPhysicalHistoryResponse(physical.getMeasureDate(), value);
    }
}
