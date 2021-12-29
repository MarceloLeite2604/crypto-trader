package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPatternChecker implements PatternChecker {

  private final PatternType patternType;

  private final int minAmountOfCandles;

  @Setter
  private PatternChecker next;

  @Override
  public PatternCheckContext check(PatternCheckContext patternCheckContext) {

    if (patternCheckContext.getCandles()
      .size() >= minAmountOfCandles) {

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
          final var analysedCandles = candles.subList(
            candles.size() - minAmountOfCandles,
            candles.size());

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
