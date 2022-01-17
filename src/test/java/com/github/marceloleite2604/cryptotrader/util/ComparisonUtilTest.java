package com.github.marceloleite2604.cryptotrader.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ComparisonUtilTest {

  private ComparisonUtil comparisonUtil;

  private static Stream<Arguments> provideValuesForTest() {
    return Stream.of(
      Arguments.of(
        BigDecimal.valueOf(5),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(0.4),
        1),
      Arguments.of(
        BigDecimal.valueOf(3),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(0.3),
        0),
      Arguments.of(
        BigDecimal.valueOf(-2),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(0.2),
        0),
      Arguments.of(
        BigDecimal.valueOf(1),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(0.1),
        0),
      Arguments.of(
        BigDecimal.valueOf(-6),
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(0.5),
        -1),
      Arguments.of(
        BigDecimal.valueOf(2),
        BigDecimal.valueOf(0),
        BigDecimal.valueOf(0),
        0)
    );
  }

  @BeforeEach
  public void setUp() {
    comparisonUtil = new ComparisonUtil();
  }

  @ParameterizedTest
  @MethodSource("provideValuesForTest")
  void shouldReturnExpectedValue(BigDecimal first, BigDecimal second, BigDecimal margin, int expected) {
    final var actual = comparisonUtil.compareRatioUsingMargin(first, second, margin);

    assertThat(actual).isEqualTo(expected);
  }

}