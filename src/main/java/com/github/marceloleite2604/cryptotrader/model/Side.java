package com.github.marceloleite2604.cryptotrader.model;

import org.springframework.util.Assert;

import java.util.stream.Stream;

public enum Side {
  BUY,
  SELL;

  public static Side findByName(String value) {
    Assert.notNull(value, "Value must be informed.");

    return Stream.of(values())
      .filter(symbol -> symbol.name()
        .equalsIgnoreCase(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find size for \"%s\".", value)));
  }
}
