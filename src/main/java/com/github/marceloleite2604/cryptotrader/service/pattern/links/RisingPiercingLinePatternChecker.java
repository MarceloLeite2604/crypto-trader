package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePosition;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleProportion;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RisingPiercingLinePatternChecker extends AbstractPatternChecker {

  private static final List<CandleProportion> VALID_CANDLE_SIZE_CATEGORIES = List.of(
    CandleProportion.VERY_LARGE,
    CandleProportion.ENORMOUS);

  public RisingPiercingLinePatternChecker() {
    super(PatternType.RISING_PIERCING_LINE, 3);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {
    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);
    final var firstComparison = firstCandle.getComparison();

    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(firstComparison.getBodyProportion())) {
      return Optional.empty();
    }


    if (CandleDirection.DESCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);
    final var secondComparison = secondCandle.getComparison();


    if (!VALID_CANDLE_SIZE_CATEGORIES.contains(secondComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (CandleDirection.ASCENDING.equals(secondCandle.getDirection())) {
      return Optional.empty();
    }

    if (!CandlePosition.LOWERED.equals(secondComparison.getPosition())) {
      return Optional.empty();
    }

    if (secondCandle.getClose()
      .compareTo(firstCandle.getBodyAverage()) <= 0) {
      return Optional.empty();
    }

    log.debug("First candle: {}", firstCandle);
    log.debug("Second candle: {}", secondCandle);

    return Optional.of(firstCandle);
  }
}
