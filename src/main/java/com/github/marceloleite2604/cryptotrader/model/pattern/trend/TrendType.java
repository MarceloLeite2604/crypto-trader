package com.github.marceloleite2604.cryptotrader.model.pattern.trend;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
public enum TrendType {
  UNDEFINED(v -> v == 0),
  DOWNTREND(v -> v > 0),
  UPTREND(v -> v < 1);

  private final Function<Integer, Boolean> comparisonResultMatcher;

  public static TrendType findByComparingCandles(final Candle first, final Candle second) {
    final var comparisonResult = first.getClose()
      .compareTo(second.getClose());

    return Arrays.stream(TrendType.values())
      .filter(type -> type.comparisonResultMatcher.apply(comparisonResult))
      .findFirst()
      .orElseThrow(
        () -> {
          final var message = String.format(
            "Could not find trend type for candles %s and %s. Comparison result is %d.",
            first,
            second,
            comparisonResult);
          return new IllegalStateException(message);
        });
  }
}
