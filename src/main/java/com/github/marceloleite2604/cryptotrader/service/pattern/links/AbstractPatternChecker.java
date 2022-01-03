package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.service.pattern.TrendService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPatternChecker implements PatternChecker {

  private final PatternType patternType;

  private final int minimalCandlesAmount;

  private final int patternCandlesSize;

  private final TrendService trendService;

  @Setter
  private PatternChecker next;

  protected boolean trendDoesNotMatchMinimal(
    PatternCheckContext patternCheckContext,
    TrendType trendType,
    int minimalTrendSize) {

    final var candles = patternCheckContext.getCandles();
    final var analyzedCandles = candles.subList(0, candles.size() - patternCandlesSize);

    final var trend = trendService.search(analyzedCandles);

    return (!trend.getType()
      .equals(trendType) || trend.getCandles()
      .size() < minimalTrendSize);
  }

  @Override
  public PatternCheckContext check(PatternCheckContext patternCheckContext) {

    if (patternCheckContext.getCandles()
      .size() >= minimalCandlesAmount) {

      findMatch(patternCheckContext).ifPresent(candle ->
      {
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
      });
    }

    if (next == null) {
      return patternCheckContext;
    }

    return next.check(patternCheckContext);
  }

  public abstract Optional<Candle> findMatch(PatternCheckContext patternCheckContext);
}
