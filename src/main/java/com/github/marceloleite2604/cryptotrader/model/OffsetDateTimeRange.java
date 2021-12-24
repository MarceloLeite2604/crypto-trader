package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class OffsetDateTimeRange {

  private final OffsetDateTime start;

  private final OffsetDateTime end;

  public boolean isBetween(OffsetDateTime offsetDateTime) {
    return start.compareTo(offsetDateTime) <= 0 &&
      end.compareTo(offsetDateTime) > 0;
  }

  public Duration getDuration() {
    return Duration.between(start, end);
  }
}
