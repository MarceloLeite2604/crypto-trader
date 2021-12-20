package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Active {
  ETHEREUM("ETH", "BRL", "Ethereum");

  private final String base;

  private final String quote;

  private final String name;

  public static Active findBySymbol(String symbol) {
    if (symbol == null) {
      return null;
    }

    return Stream.of(values())
      .filter(active -> active.getSymbol()
        .equals(symbol))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find active for symbol \"%s\".", symbol)));
  }

  public String getSymbol() {
    return base + "-" + quote;
  }
}
