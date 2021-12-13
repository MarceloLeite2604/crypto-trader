package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class Ticker {

  private final BigDecimal buy;

  private final BigDecimal high;

  private final BigDecimal last;

  private final BigDecimal low;

  private final BigDecimal open;

  @EqualsAndHashCode.Include
  private final String pair;

  private final BigDecimal sell;

  private final BigDecimal volume;
}
