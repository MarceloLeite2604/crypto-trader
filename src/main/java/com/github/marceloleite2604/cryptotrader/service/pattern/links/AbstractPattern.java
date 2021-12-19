package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractPattern implements Pattern {

  private final PatternType patternType;

  private final int minAmountOfCandles;

  @Setter
  private Pattern next;

  @Override
  public PatternCheckContext check(PatternCheckContext patternCheckContext) {

    if (patternCheckContext.getCandles()
      .size() >= minAmountOfCandles) {

      findMatch(patternCheckContext).ifPresent(candle ->
        patternCheckContext.addMatch(PatternMatch.builder()
          .candleTime(candle.getTimestamp())
          .type(patternType)
          .symbol(candle.getSymbol())
          .build()));
    }

    if (next == null) {
      return patternCheckContext;
    }

    return next.check(patternCheckContext);
  }

  public abstract Optional<Candle> findMatch(PatternCheckContext patternCheckContext);
}
