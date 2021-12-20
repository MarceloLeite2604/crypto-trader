package com.github.marceloleite2604.cryptotrader.model.converter;

import com.github.marceloleite2604.cryptotrader.model.Active;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActiveConverter implements AttributeConverter<Active, String> {

  @Override
  public String convertToDatabaseColumn(Active active) {
    return (active == null ? null : active.getSymbol());
  }

  @Override
  public Active convertToEntityAttribute(String symbol) {
    return (symbol == null ? null : Active.findBySymbol(symbol));
  }
}
