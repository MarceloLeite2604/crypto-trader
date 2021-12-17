package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Symbol {
  ETHEREUM("ETH-BRL", "Ethereum");

  private final String value;

  private final String name;

  public static Symbol findByValue(String value) {
    Assert.notNull(value, "Value must be informed.");

    return Stream.of(values())
      .filter(symbol -> symbol.getValue()
        .equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find symbol for \"%s\".", value)));
  }
}
