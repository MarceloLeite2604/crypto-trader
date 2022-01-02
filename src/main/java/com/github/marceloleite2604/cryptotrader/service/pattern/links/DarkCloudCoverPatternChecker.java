package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleProportion;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternCheckContext;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.model.pattern.trend.TrendType;
import com.github.marceloleite2604.cryptotrader.service.pattern.TrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DarkCloudCoverPatternChecker extends AbstractPatternChecker {

  private static final int MINIMAL_CANDLES_AMOUNT = 6;
  private static final int PATTERN_CANDLES_SIZE = 3;
  private static final int MINIMAL_TREND_SIZE = MINIMAL_CANDLES_AMOUNT - PATTERN_CANDLES_SIZE;

  private static final List<CandleProportion> VALID_CANDLE_SIZE_CATEGORIES = List.of(
    CandleProportion.MEDIUM_LARGE,
    CandleProportion.LARGE,
    CandleProportion.VERY_LARGE,
    CandleProportion.ENORMOUS);

  public DarkCloudCoverPatternChecker(TrendService trendService) {
    super(PatternType.DARK_CLOUD_COVER, MINIMAL_CANDLES_AMOUNT, PATTERN_CANDLES_SIZE, trendService);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    if (trendDoesNotMatchMinimal(patternCheckContext, TrendType.UPTREND, MINIMAL_TREND_SIZE)) {
      return Optional.empty();
    }

    final var candles = patternCheckContext.getCandles();

    final var secondCandle = candles.get(1);
    final var secondComparison = secondCandle.getComparison();

    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(secondComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (!CandleDirection.DESCENDING.equals(secondCandle.getDirection())) {
      return Optional.empty();
    }

    final var thirdCandle = candles.get(2);
    final var thirdComparison = thirdCandle.getComparison();

    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(thirdComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (!CandleDirection.ASCENDING.equals(thirdCandle.getDirection())) {
      return Optional.empty();
    }

    if (secondCandle.getOpen()
      .compareTo(thirdCandle.getClose()) <= 0) {
      return Optional.empty();
    }

    if (secondCandle.getClose()
      .compareTo(thirdCandle.getBodyAverage()) >= 0) {
      return Optional.empty();
    }

    final var firstCandle = candles.get(0);

    if (firstCandle.getClose()
      .compareTo(secondCandle.getClose()) >= 0) {
      return Optional.empty();
    }

    return Optional.of(secondCandle);
  }
}
