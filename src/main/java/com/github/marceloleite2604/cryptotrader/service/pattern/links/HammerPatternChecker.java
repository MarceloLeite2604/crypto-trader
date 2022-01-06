package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePosition;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleType;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.service.pattern.TrendService;
import com.github.marceloleite2604.cryptotrader.util.ComparisonUtil;
import com.github.marceloleite2604.cryptotrader.util.StatisticsUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HammerPatternChecker extends AbstractPatternChecker {

  private static final int MINIMAL_CANDLES_AMOUNT = 5;
  private static final int PATTERN_CANDLES_SIZE = 2;
  private static final int MINIMAL_TREND_SIZE = MINIMAL_CANDLES_AMOUNT - PATTERN_CANDLES_SIZE;

  private static final List<CandleType> ACCEPTED_CANDLE_TYPES = List.of(
    CandleType.HAMMER,
    CandleType.SHOOTING_STAR,
    CandleType.DRAGONFLY);

  public HammerPatternChecker(TrendService trendService, StatisticsUtil statisticsUtil, ComparisonUtil comparisonUtil) {
    super(PatternType.HAMMER, MINIMAL_CANDLES_AMOUNT, PATTERN_CANDLES_SIZE, trendService, statisticsUtil, comparisonUtil);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    if (trendDoesNotMatchMinimal(patternCheckContext, TrendType.DOWNTREND, MINIMAL_TREND_SIZE)) {
      return Optional.empty();
    }

    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);
    final var firstComparison = firstCandle.getComparison();

    if (!CandleDirection.ASCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

//    if (!CandlePosition.RAISED.equals(firstComparison.getPosition())) {
//      return Optional.empty();
//    }

    final var secondCandle = candles.get(1);

    if (firstCandle.getClose().compareTo(secondCandle.getClose()) <= 0) {
      return Optional.empty();
    }

    if (!ACCEPTED_CANDLE_TYPES.contains(secondCandle.getType())) {
      return Optional.empty();
    }

    return Optional.of(firstCandle);
  }
}
