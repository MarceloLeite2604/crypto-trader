package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CandlePrecision {

  FIFTEEN_MINUTES("15m"),
  THIRTY_MINUTES("30m"),
  ONE_HOUR("1h"),
  ONE_DAY("1d"),
  ONE_WEEK("1w"),
  ONE_MONTH("1M");

  private final String value;
}
