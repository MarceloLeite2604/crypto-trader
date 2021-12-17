package com.github.marceloleite2604.cryptotrader.model.patterns.links;

import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleSizeCategory;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FallingPiercingLinePattern extends AbstractPattern {

  private static final List<CandleSizeCategory> VALID_CANDLE_SIZE_CATEGORIES = List.of(
    CandleSizeCategory.LARGE,
    CandleSizeCategory.VERY_LARGE,
    CandleSizeCategory.ENORMOUS);

  public FallingPiercingLinePattern() {
    super(PatternType.FALLING_PIERCING_LINE, 3);
  }

  @Override
  public Optional<CandleAnalysis> findMatch(PatternCheckContext patternCheckContext) {
    final var candleAnalyses = patternCheckContext.getCandleAnalyses();

    final var firstAnalysis = candleAnalyses.get(0);

    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(firstAnalysis.getBodySizeCategory())) {
      return Optional.empty();
    }

    if (CandleDirection.ASCENDING.equals(firstAnalysis.getDirection())) {
      return Optional.empty();
    }

    final var secondAnalysis = candleAnalyses.get(1);


    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(secondAnalysis.getBodySizeCategory())) {
      return Optional.empty();
    }

    if (CandleDirection.DESCENDING.equals(secondAnalysis.getDirection())) {
      return Optional.empty();
    }

    return Optional.of(firstAnalysis);
  }
}
