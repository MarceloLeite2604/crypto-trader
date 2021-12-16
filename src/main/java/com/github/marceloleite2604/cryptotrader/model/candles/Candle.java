package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Candle {

  public Candle(Candle other) {
    this.close = other.close;

    this.high = other.high;

    this.low =other.low;

    this.open = other.open;

    this.precision = other.precision;

    this.symbol = other.symbol;

    this.timestamp = other.timestamp;

    this.volume = other.volume;
  }

  private final BigDecimal close;

  private final BigDecimal high;

  private final BigDecimal low;

  private final BigDecimal open;

  private final CandlePrecision precision;

  private final String symbol;

  private final OffsetDateTime timestamp;

  private final BigDecimal volume;
}
