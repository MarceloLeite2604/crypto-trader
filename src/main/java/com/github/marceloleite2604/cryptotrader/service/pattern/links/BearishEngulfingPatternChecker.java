package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleProportion;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.service.pattern.TrendService;
import com.github.marceloleite2604.cryptotrader.util.ComparisonUtil;
import com.github.marceloleite2604.cryptotrader.util.StatisticsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BearishEngulfingPatternChecker extends AbstractPatternChecker {

  private static final int MINIMAL_CANDLES_AMOUNT = 4;
  private static final int PATTERN_CANDLES_SIZE = 2;
  private static final int MINIMAL_TREND_SIZE = MINIMAL_CANDLES_AMOUNT - PATTERN_CANDLES_SIZE;

  private static final List<CandleProportion> VALID_BODY_SIZE_CATEGORIES = List.of(
    CandleProportion.MEDIUM,
    CandleProportion.MEDIUM_LARGE,
    CandleProportion.LARGE,
    CandleProportion.VERY_LARGE,
    CandleProportion.ENORMOUS);

  public BearishEngulfingPatternChecker(TrendService trendService, StatisticsUtil statisticsUtil, ComparisonUtil comparisonUtil) {
    super(PatternType.BEARISH_ENGULFING, MINIMAL_CANDLES_AMOUNT, PATTERN_CANDLES_SIZE, trendService, statisticsUtil, comparisonUtil);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    if (trendDoesNotMatchMinimal(patternCheckContext, TrendType.UPTREND, MINIMAL_TREND_SIZE)) {
      return Optional.empty();
    }

    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);
    final var firstComparison = firstCandle.getComparison();

    if (!CandleDirection.DESCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);
    final var secondComparison = secondCandle.getComparison();

    if (!CandleDirection.ASCENDING.equals(secondCandle.getDirection())) {
      return Optional.empty();
    }

    if (!VALID_BODY_SIZE_CATEGORIES.contains(firstComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (!VALID_BODY_SIZE_CATEGORIES.contains(secondComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (firstCandle.getBodySize()
      .compareTo(BigDecimal.ZERO) == 0) {
      return Optional.empty();
    }

    if (secondCandle.getBodySize()
      .compareTo(BigDecimal.ZERO) == 0) {
      return Optional.empty();
    }

    if (firstCandle.getOpen()
      .compareTo(secondCandle.getHigh()) < 0) {
      return Optional.empty();
    }

    if (firstCandle.getClose()
      .compareTo(secondCandle.getLow()) > 0) {
      return Optional.empty();
    }

    return Optional.of(firstCandle);
  }
}
