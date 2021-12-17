package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HammerPattern extends AbstractPattern {

  public HammerPattern() {
    super(PatternType.HAMMER, 4);
  }

  @Override
  public Optional<CandleAnalysis> findMatch(PatternCheckContext patternCheckContext) {
    final var candleAnalyses = patternCheckContext.getCandleAnalyses();

    final var firstAnalysis = candleAnalyses.get(0);

    if (!CandleDirection.ASCENDING.equals(firstAnalysis.getDirection())) {
      return Optional.empty();
    }

    final var secondAnalysis = candleAnalyses.get(1);

    if (secondAnalysis.getAverage()
      .compareTo(firstAnalysis.getAverage()) > 0) {
      return Optional.empty();
    }

    if (!CandleType.HAMMER.equals(secondAnalysis.getType()) ||
      !CandleDirection.ASCENDING.equals(secondAnalysis.getDirection())) {
      return Optional.empty();
    }

    final var thirdAnalysis = candleAnalyses.get(2);

    if (!CandleDirection.DESCENDING.equals(thirdAnalysis.getDirection())) {
      return Optional.empty();
    }

    if (thirdAnalysis.getCandle()
      .getLow()
      .compareTo(secondAnalysis.getCandle()
        .getLow()) < 0) {
      return Optional.empty();
    }

    final var fourthAnalysis = candleAnalyses.get(3);

    if (!CandleDirection.DESCENDING.equals(fourthAnalysis.getDirection())) {
      return Optional.empty();
    }

    if (thirdAnalysis.getAverage()
      .compareTo(fourthAnalysis.getAverage()) > 0) {
      return Optional.empty();
    }

    return Optional.of(secondAnalysis);
  }
}
