package com.github.marceloleite2604.cryptotrader.model.pattern.trend;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Trend {

  private final List<Candle> candles;

  private final TrendType type;
}
