package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Candle {

  private final BigDecimal close;

  private final BigDecimal high;

  private final BigDecimal low;

  private final BigDecimal open;

  private final CandlePrecision precision;

  private final String symbol;

  private final OffsetDateTime timestamp;

  private final BigDecimal volume;
}
