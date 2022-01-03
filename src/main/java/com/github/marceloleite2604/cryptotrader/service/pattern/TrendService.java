package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.Trend;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrendService {

  public Trend search(final List<Candle> candles) {

    if (candles.size() <= 1) {
      return Trend.builder()
        .type(TrendType.UNDEFINED)
        .candles(candles)
        .build();
    }

    Candle previousCandle = null;
    TrendType trendType = null;
    List<Candle> trendCandles = new ArrayList<>();
    for (Candle candle : candles) {
      if (previousCandle != null) {
        final var currentTrendType = TrendType.findByComparingCandles(previousCandle, candle);
        if (trendType == null) {
          trendType = currentTrendType;
          trendCandles.add(previousCandle);
          trendCandles.add(candle);
        } else {
          if (trendType.equals(currentTrendType)) {
            trendCandles.add(candle);
          } else {
            break;
          }
        }
      }
      previousCandle = candle;
    }

    return Trend.builder()
      .type(trendType)
      .candles(trendCandles)
      .build();
  }
}
