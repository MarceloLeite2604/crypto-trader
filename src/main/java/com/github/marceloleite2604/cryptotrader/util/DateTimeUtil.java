package com.github.marceloleite2604.cryptotrader.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

@Component
public class DateTimeUtil {

  public OffsetDateTime convertEpochToUtcOffsetDateTime(long epoch) {
    final var localDateTime = LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
    return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
  }

  public OffsetDateTime convertTimestampWithNanosToUtcOffsetDateTime(long timestampWithNanos) {
    long epochSecond = timestampWithNanos / 1_000_000_000L;
    long nanoAdjustment = timestampWithNanos % 1_000_000_000L;
    final var instant = Instant.ofEpochSecond(epochSecond, nanoAdjustment);
    return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
  }

  public String formatOffsetDateTimeAsString(OffsetDateTime offsetDateTime) {
    return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.forLanguageTag("pt-BR"))
      .format(offsetDateTime);
  }
}
