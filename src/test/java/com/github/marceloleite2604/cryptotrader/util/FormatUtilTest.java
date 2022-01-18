package com.github.marceloleite2604.cryptotrader.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FormatUtilTest {

  private FormatUtil formatUtil;

  @BeforeEach
  public void setUp() {
    formatUtil = new FormatUtil();
  }

  @Test
  void shouldPresentValueAsBrazilianRealCurrency() {
    final var value = BigDecimal.valueOf(12.89);
    final var expected = "R$\u00A012,89";

    final var actual = formatUtil.toBrl(value);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldPresentValueAsPercentage() {
    final var value = BigDecimal.valueOf(0.1234);
    final var expected = "12,34%";

    final var actual = formatUtil.toPercentage(value);

    assertThat(actual).isEqualTo(expected);
  }

}