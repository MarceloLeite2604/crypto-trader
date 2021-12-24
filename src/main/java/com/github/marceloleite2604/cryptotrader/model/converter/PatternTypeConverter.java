package com.github.marceloleite2604.cryptotrader.model.converter;

import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PatternTypeConverter implements AttributeConverter<PatternType, String> {

  @Override
  public String convertToDatabaseColumn(PatternType patternType) {
    return (patternType == null ? null : patternType.getName());
  }

  @Override
  public PatternType convertToEntityAttribute(String name) {
    return (name == null ? null : PatternType.findByName(name));
  }
}
