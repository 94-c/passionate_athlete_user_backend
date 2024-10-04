package com.backend.athlete.user.athlete.converter;

import com.backend.athlete.user.athlete.domain.type.AthleteSuccessType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AthleteSuccessTypeConverter implements AttributeConverter<AthleteSuccessType, String> {

    @Override
    public String convertToDatabaseColumn(AthleteSuccessType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public AthleteSuccessType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AthleteSuccessType.valueOf(dbData);
    }
}
