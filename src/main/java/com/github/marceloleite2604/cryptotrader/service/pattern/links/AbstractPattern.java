package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternType;
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

    if (patternCheckContext.getCandleAnalyses()
      .size() >= minAmountOfCandles) {

      findMatch(patternCheckContext).ifPresent(candleAnalysis ->
        patternCheckContext.addMatch(PatternMatch.builder()
          .candleTime(candleAnalysis.getCandle()
            .getTimestamp())
          .type(patternType)
          .symbol(candleAnalysis.getCandle()
            .getSymbol())
          .build()));
    }

    if (next == null) {
      return patternCheckContext;
    }

    return next.check(patternCheckContext);
  }

  public abstract Optional<CandleAnalysis> findMatch(PatternCheckContext patternCheckContext);
}
