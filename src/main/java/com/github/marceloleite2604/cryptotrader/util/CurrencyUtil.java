package com.github.marceloleite2604.cryptotrader.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class CurrencyUtil {

  private final NumberFormat brlNumberFormat;

  public CurrencyUtil() {
    this.brlNumberFormat = createBrlNumberFormat();
  }

  private NumberFormat createBrlNumberFormat() {
    NumberFormat brlNumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
    brlNumberFormat.setMinimumFractionDigits(2);
    brlNumberFormat.setMaximumFractionDigits(2);
    return brlNumberFormat;
  }

  public String toBrl(BigDecimal bigDecimal) {
    return brlNumberFormat.format(bigDecimal.doubleValue());
  }
}
