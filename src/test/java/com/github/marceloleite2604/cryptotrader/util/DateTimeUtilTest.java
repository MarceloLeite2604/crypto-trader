package com.github.marceloleite2604.cryptotrader.util;

import com.github.marceloleite2604.cryptotrader.model.OffsetDateTimeRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTimeUtilTest {

  private DateTimeUtil dateTimeUtil;

  @BeforeEach
  public void setUp() {
    dateTimeUtil = new DateTimeUtil();
  }

  @Test
  void shouldReturnMatchingUffOffsetDateTime() {
    final var epoch = 1642387174L;
    final var expected = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 39, 34),
      ZoneOffset.UTC);

    final var actual = dateTimeUtil.convertEpochToUtcOffsetDateTime(epoch);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnMatchingUffOffsetDateTimeWhenConvertingTimestampWithNanos() {
    final var epoch = 1642387422123456789L;
    final var expected = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 43, 42, 123456789),
      ZoneOffset.UTC);

    final var actual = dateTimeUtil.convertTimestampWithNanosToUtcOffsetDateTime(epoch);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnRangeSplit() {
    final var start = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 0, 0),
      ZoneOffset.UTC);
    final var end = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 58, 0),
      ZoneOffset.UTC);
    final var offsetDateTimeRange = OffsetDateTimeRange.builder()
      .start(start)
      .end(end)
      .build();
    final var duration = Duration.ofMinutes(15);

    final var expected = List.of(
      OffsetDateTimeRange.builder()
        .start(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 0, 0),
          ZoneOffset.UTC))
        .end(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 15, 0),
          ZoneOffset.UTC))
        .build(),
      OffsetDateTimeRange.builder()
        .start(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 15, 0),
          ZoneOffset.UTC))
        .end(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 30, 0),
          ZoneOffset.UTC))
        .build(),
      OffsetDateTimeRange.builder()
        .start(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 30, 0),
          ZoneOffset.UTC))
        .end(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 45, 0),
          ZoneOffset.UTC))
        .build(),
      OffsetDateTimeRange.builder()
        .start(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 45, 0),
          ZoneOffset.UTC))
        .end(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 58, 0),
          ZoneOffset.UTC))
        .build());

    final var actual = dateTimeUtil.splitRange(offsetDateTimeRange, duration);

    assertThat(actual).containsAll(expected);
  }

  @Test
  void shouldReturnArgumentRangeWhenDurationIsBiggerThanRange() {
    final var start = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 0, 0),
      ZoneOffset.UTC);
    final var end = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 58, 0),
      ZoneOffset.UTC);
    final var offsetDateTimeRange = OffsetDateTimeRange.builder()
      .start(start)
      .end(end)
      .build();
    final var duration = Duration.ofDays(1);

    final var expected = List.of(
      OffsetDateTimeRange.builder()
        .start(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 0, 0),
          ZoneOffset.UTC))
        .end(OffsetDateTime.of(
          LocalDateTime.of(2022, 1, 17, 2, 58, 0),
          ZoneOffset.UTC))
        .build());

    final var actual = dateTimeUtil.splitRange(offsetDateTimeRange, duration);

    assertThat(actual).containsAll(expected);
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenTimeRangeIsNull() {
    final var duration = Duration.ofMinutes(15);
    assertThrows(IllegalArgumentException.class, () -> dateTimeUtil.splitRange(null, duration));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenDurationIsNull() {
    final var start = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 0, 0),
      ZoneOffset.UTC);
    final var end = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 3, 0, 0),
      ZoneOffset.UTC);
    final var offsetDateTimeRange = OffsetDateTimeRange.builder()
      .start(start)
      .end(end)
      .build();

    assertThrows(IllegalArgumentException.class, () -> dateTimeUtil.splitRange(offsetDateTimeRange, null));
  }

  @Test
  void shouldTruncateToFifteenMinutes() {
    final var offsetDateTime = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 17, 0),
      ZoneOffset.UTC);
    final var duration = Duration.ofMinutes(15);
    final var expected = OffsetDateTime.of(
      LocalDateTime.of(2022, 1, 17, 2, 15, 0),
      ZoneOffset.UTC);

    final var actual = dateTimeUtil.truncateTo(offsetDateTime, duration);

    assertThat(actual).isEqualTo(expected);
  }
}