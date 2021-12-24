package com.github.marceloleite2604.cryptotrader.model.converter;

import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CandlePrecisionConverter implements AttributeConverter<CandlePrecision, String> {

  @Override
  public String convertToDatabaseColumn(CandlePrecision candlePrecision) {
    return (candlePrecision == null ? null : candlePrecision.getValue());
  }

  @Override
  public CandlePrecision convertToEntityAttribute(String value) {
    return (value == null ? null : CandlePrecision.findByValue(value));
  }
}
