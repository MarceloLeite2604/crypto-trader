package com.github.marceloleite2604.cryptotrader.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class StatisticsUtilTest {

  private StatisticsUtil statisticsUtil;

  @BeforeEach
  public void setUp() {
    statisticsUtil = new StatisticsUtil();
  }

  @Test
  void shouldReturnCorrectStandardDeviationForClassWithBigDecimalGetter() {
    final var values = List.of(
      new Wrapper(BigDecimal.valueOf(7)),
      new Wrapper(BigDecimal.valueOf(4)),
      new Wrapper(BigDecimal.valueOf(13))
    );
    final Function<Wrapper, BigDecimal> getter = Wrapper::getBigDecimal;
    final var expected = BigDecimal.valueOf(4.582575695);

    final var actual = statisticsUtil.calculateStandardDeviation(values, getter);

    assertThat(actual).isCloseTo(expected, offset(BigDecimal.valueOf(1e-9)));
  }

  @Test
  void shouldReturnCorrectStandardDeviation() {
    final var values = List.of(
      BigDecimal.valueOf(21),
      BigDecimal.valueOf(3),
      BigDecimal.valueOf(58)
    );
    final var expected = BigDecimal.valueOf(28.04163571);

    final var actual = statisticsUtil.calculateStandardDeviation(values);

    assertThat(actual).isCloseTo(expected, offset(BigDecimal.valueOf(1e-8)));
  }

  @Test
  void shouldReturnCorrectAverage() {
    final var values = List.of(
      new Wrapper(BigDecimal.valueOf(17)),
      new Wrapper(BigDecimal.valueOf(72)),
      new Wrapper(BigDecimal.valueOf(33))
    );
    final Function<Wrapper, BigDecimal> getter = Wrapper::getBigDecimal;
    final var expected = BigDecimal.valueOf(40.6666667);

    final var actual = statisticsUtil.calculateAverage(values, getter);

    assertThat(actual).isCloseTo(expected, offset(BigDecimal.valueOf(1e-7)));
  }

  @AllArgsConstructor
  @Getter
  static class Wrapper {
    private final BigDecimal bigDecimal;
  }
}