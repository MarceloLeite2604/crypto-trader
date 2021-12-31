package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleType;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HammerPatternChecker extends AbstractPatternChecker {

  public HammerPatternChecker() {
    super(PatternType.HAMMER, 4);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {
    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);

    if (!CandleDirection.ASCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);

    if (secondCandle.getAverage()
      .compareTo(firstCandle.getAverage()) > 0) {
      return Optional.empty();
    }

    if (!CandleType.HAMMER.equals(secondCandle.getType()) ||
      !CandleDirection.ASCENDING.equals(secondCandle.getDirection())) {
      return Optional.empty();
    }

    final var thirdCandle = candles.get(2);

    if (!CandleDirection.DESCENDING.equals(thirdCandle.getDirection())) {
      return Optional.empty();
    }

    if (thirdCandle.getLow()
      .compareTo(secondCandle.getLow()) < 0) {
      return Optional.empty();
    }

    final var fourthCandle = candles.get(3);

    if (!CandleDirection.DESCENDING.equals(fourthCandle.getDirection())) {
      return Optional.empty();
    }

    if (thirdCandle.getAverage()
      .compareTo(fourthCandle.getAverage()) > 0) {
      return Optional.empty();
    }

    return Optional.of(firstCandle);
  }
}
