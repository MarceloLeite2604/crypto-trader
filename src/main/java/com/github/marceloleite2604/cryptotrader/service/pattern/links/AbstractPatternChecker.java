package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.service.pattern.TrendService;
import com.github.marceloleite2604.cryptotrader.util.ComparisonUtil;
import com.github.marceloleite2604.cryptotrader.util.StatisticsUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPatternChecker implements PatternChecker {

  private final PatternType patternType;

  private final int minimalCandlesAmount;

  private final int patternCandlesSize;

  private final TrendService trendService;

  private final StatisticsUtil statisticsUtil;

  private final ComparisonUtil comparisonUtil;

  @Setter
  private PatternChecker next;

  protected boolean trendDoesNotMatchMinimal(
    PatternCheckContext patternCheckContext,
    TrendType trendType,
    int minimalTrendSize) {

    /*final var candles = patternCheckContext.getCandles();
    final var analyzedCandles = candles.subList(patternCandlesSize, candles.size());

    final var trend = trendService.search(analyzedCandles);

    return (!trend.getType()
      .equals(trendType) || trend.getCandles()
      .size() < minimalTrendSize);*/

    var trendCandles = patternCheckContext.getCandles()
      .subList(
        patternCandlesSize,
        patternCheckContext.getCandles()
          .size());

    final var closeAverage = statisticsUtil.calculateAverage(trendCandles, Candle::getClose);
    final var closeStandardDeviation = statisticsUtil.calculateStandardDeviation(trendCandles, Candle::getClose);

    final var secondHalfSumCloseDiff = trendCandles
      .subList(0, trendCandles.size() / 2)
      .stream()
      .map(trendCandle -> trendCandle.getClose()
        .subtract(closeAverage))
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    final var multiplier = BigDecimal.valueOf(0);

    if (Side.SELL.equals(patternType.getSide())) {
      return secondHalfSumCloseDiff.compareTo(
        closeStandardDeviation.multiply(multiplier)) <= 0;
    }

    return secondHalfSumCloseDiff.compareTo(
      closeStandardDeviation.negate().multiply(multiplier)) >= 0;
  }

  @Override
  public PatternCheckContext check(final PatternCheckContext patternCheckContext) {

    if (patternCheckContext.getCandles()
      .size() >= minimalCandlesAmount) {

      findMatch(patternCheckContext).ifPresent(candle ->
      {
//        var trendCandles = patternCheckContext.getCandles()
//          .subList(
//            patternCandlesSize,
//            patternCheckContext.getCandles()
//              .size());
//
//        final var closeAverage = statisticsUtil.calculateAverage(trendCandles, Candle::getClose);
//        final var closeStandardDeviation = statisticsUtil.calculateStandardDeviation(trendCandles, Candle::getClose);
//
//        final var secondHalfSumCloseDiff = trendCandles
//          .subList(0, trendCandles.size() / 2)
//          .stream()
//          .map(trendCandle -> trendCandle.getClose()
//            .subtract(closeAverage))
//          .reduce(BigDecimal.ZERO, BigDecimal::add);

//        log.info("Close average: {}", closeAverage);
//        log.info("Close std dev: {}", closeStandardDeviation);
//        log.info("Sum close diff: {}", secondHalfSumCloseDiff);

//        if ((Side.SELL.equals(patternType.getSide()) &&
//          secondHalfSumCloseDiff.compareTo(BigDecimal.ZERO) > 0) ||
//          (Side.BUY.equals(patternType.getSide()) &&
//            secondHalfSumCloseDiff.compareTo(BigDecimal.ZERO) < 0)) {
        final var active = Active.findBySymbol(candle.getSymbol());

        patternCheckContext.addMatch(PatternMatch.builder()
          .candleTime(candle.getTimestamp())
          .type(patternType)
          .candlePrecision(candle.getPrecision())
          .active(active)
          .build());

        if (log.isDebugEnabled()) {
          final var candles = patternCheckContext.getCandles();
          final var analysedCandles = candles.subList(0, patternCandlesSize);

          log.debug("{} pattern found analysing the following candles: {}",
            patternType.getName(),
            analysedCandles);
        }
//        }
      });
    }

    if (next == null) {
      return patternCheckContext;
    }

    return next.check(patternCheckContext);
  }

  public abstract Optional<Candle> findMatch(PatternCheckContext patternCheckContext);
}
