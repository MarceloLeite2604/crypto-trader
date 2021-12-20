package com.github.marceloleite2604.cryptotrader.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class FormatUtil {

  private final NumberFormat brlNumberFormat;

  private final NumberFormat percentageFormat;

  private final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  public FormatUtil() {
    this.brlNumberFormat = createBrlNumberFormat();
    this.percentageFormat = createPercentageFormat();
  }

  private NumberFormat createPercentageFormat() {
    final var percentageFormat = NumberFormat.getPercentInstance();
    percentageFormat.setMinimumFractionDigits(2);
    return percentageFormat;
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

  public String toPercentage(BigDecimal bigDecimal) {
    return percentageFormat.format(bigDecimal.doubleValue());
  }
}
