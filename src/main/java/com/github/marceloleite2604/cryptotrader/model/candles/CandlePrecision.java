package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CandlePrecision {

  FIFTEEN_MINUTES("15m", Duration.ofMinutes(15)),
  THIRTY_MINUTES("30m", Duration.ofMinutes(30)),
  ONE_HOUR("1h", Duration.ofHours(1)),
  ONE_DAY("1d", Duration.ofDays(1)),
  ONE_WEEK("1w", Duration.ofDays(7)),
  ONE_MONTH("1M", Duration.ofDays(30));

  private final String value;

  private final Duration duration;
}
