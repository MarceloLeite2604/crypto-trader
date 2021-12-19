package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CandleComparison {

  private final CandlePosition position;

  private final CandleProportion candleProportion;

  private final CandleProportion bodyProportion;
}
