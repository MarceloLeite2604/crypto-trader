package com.github.marceloleite2604.cryptotrader.properties.converter;

import com.github.marceloleite2604.cryptotrader.model.Active;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class BaseToActiveConverter implements Converter<String, Active> {

  @Override
  public Active convert(String base) {
    return Active.findByBase(base);
  }
}
