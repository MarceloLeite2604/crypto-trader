package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.Trend;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.util.ComparisonUtil;
import com.github.marceloleite2604.cryptotrader.util.StatisticsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrendService {

  private final StatisticsUtil statisticsUtil;

  private final ComparisonUtil comparisonUtil;

  public Trend search(final List<Candle> candles) {

    if (candles.size() <= 1) {
      return Trend.builder()
        .type(TrendType.UNDEFINED)
        .candles(candles)
        .build();
    }

    final var averageDiffsStandardDeviation = calculateAverageDiffsStandardDeviation(candles);

    Candle previousCandle = null;
    TrendType trendType = null;
    List<Candle> trendCandles = new ArrayList<>();
    for (Candle candle : candles) {
      if (previousCandle != null) {

        final var averageDiff = previousCandle.getAverage().subtract(candle.getAverage());

        final var averageDiffComparison = comparisonUtil.compareRatioUsingMargin(averageDiff, averageDiffsStandardDeviation);

        final var currentTrendType = TrendType.findByComparisonResult(averageDiffComparison);

        if (trendType == null) {
          trendType = currentTrendType;
          trendCandles.add(previousCandle);
          trendCandles.add(candle);
        } else {
          if (trendType.equals(currentTrendType) || TrendType.UNDEFINED.equals(currentTrendType)) {
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

  private BigDecimal calculateAverageDiffsStandardDeviation(List<Candle> candles) {
    final var averageDiffs = retrieveAverageDiffs(candles);
    return statisticsUtil.calculateStandardDeviation(averageDiffs);
  }

  private List<BigDecimal> retrieveAverageDiffs(List<Candle> candles) {
    List<BigDecimal> closeDiffs = new ArrayList<>(candles.size() - 1);

    Candle previousCandle = null;

    for (Candle candle : candles) {
      if (previousCandle != null) {
        final var closeDiff = previousCandle.getClose()
          .subtract(candle.getClose());
        closeDiffs.add(closeDiff);
      }
      previousCandle = candle;
    }
    return closeDiffs;
  }
}
