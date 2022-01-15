package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Active {

  public static final Active BRL = Active.builder()
    .base("BRL")
    .quote("BRL")
    .name("Brazilian Real")
    .build();

  private static final List<Active> ACTIVES = new ArrayList<>(List.of(BRL));

  private final String base;

  private final String quote;

  private final String name;

  public static void add(Active active) {
    ACTIVES.add(active);
  }

  public static Active[] values() {
    return ACTIVES.toArray(Active[]::new);
  }

  public static Active findBySymbol(String symbol) {
    if (symbol == null) {
      return null;
    }

    return ACTIVES.stream()
      .filter(active -> active.getSymbol()
        .equals(symbol))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find active for symbol \"%s\".", symbol)));
  }

  public static Active findByBase(String base) {
    if (base == null) {
      return null;
    }

    return ACTIVES.stream()
      .filter(active -> active.getBase()
        .equals(base))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find active for base \"%s\".", base)));
  }

  public String getSymbol() {
    return base + "-" + quote;
  }
}
