package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@RequiredArgsConstructor
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
      });
    }

    if (next == null) {
      return patternCheckContext;
    }

    return next.check(patternCheckContext);
  }

  public abstract Optional<Candle> findMatch(PatternCheckContext patternCheckContext);
}
