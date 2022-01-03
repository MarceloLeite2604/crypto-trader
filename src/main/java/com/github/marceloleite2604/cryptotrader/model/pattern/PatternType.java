package com.github.marceloleite2604.cryptotrader.model.pattern;

import com.github.marceloleite2604.cryptotrader.model.Side;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatternType {
  HAMMER("Hammer", Side.BUY),
  INVERSE_HAMMER("Inverse Hammer", Side.BUY),
  DARK_CLOUD_COVER("Dark Cloud Cover", Side.SELL),
  PIERCING_LINE("Piercing Line", Side.BUY),
  BULLISH_ENGULFING("Bullish Engulfing", Side.BUY),
  BEARISH_ENGULFING("Bearish Engulfing", Side.SELL),
  MORNING_STAR("Morning Star", Side.BUY),
  EVENING_STAR("Evening Star", Side.SELL);

  private final String name;

  private final Side side;

  public static PatternType findByName(String name) {
    if (name == null) {
      return null;
    }

    return Stream.of(values())
      .filter(active -> active.getName()
        .equals(name))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find pattern type with name \"%s\".", name)));
  }
}
