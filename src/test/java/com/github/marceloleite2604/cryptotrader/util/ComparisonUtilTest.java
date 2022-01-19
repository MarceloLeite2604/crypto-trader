package com.github.marceloleite2604.cryptotrader.util;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
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

  private static Stream<Arguments> provideValuesForTestUsingDefaultMargin() {
    return Stream.of(
      Arguments.of(
        BigDecimal.valueOf(10)
          .multiply(GeneralConfiguration.COMPARISON_THRESHOLD)
          .add(BigDecimal.ONE),
        BigDecimal.valueOf(10),
        1),
      Arguments.of(
        BigDecimal.valueOf(10)
          .multiply(GeneralConfiguration.COMPARISON_THRESHOLD),
        BigDecimal.valueOf(10),
        0),
      Arguments.of(
        BigDecimal.valueOf(10)
          .multiply(GeneralConfiguration.COMPARISON_THRESHOLD)
          .negate(),
        BigDecimal.valueOf(10),
        0),
      Arguments.of(
        BigDecimal.valueOf(10)
          .multiply(GeneralConfiguration.COMPARISON_THRESHOLD)
          .add(BigDecimal.ONE)
          .negate(),
        BigDecimal.valueOf(10),
        -1)
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

  @ParameterizedTest
  @MethodSource("provideValuesForTestUsingDefaultMargin")
  void shouldReturnExpectedValueUsingDefaultMargin(BigDecimal first, BigDecimal second, int expected) {
    final var actual = comparisonUtil.compareRatioUsingMargin(first, second);

    assertThat(actual).isEqualTo(expected);
  }

}