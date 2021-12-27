package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BearishEngulfingPatternChecker extends AbstractPatternChecker {

  public BearishEngulfingPatternChecker() {
    super(PatternType.BEARISH_ENGULFING, 2);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);
    final var firstComparison = firstCandle.getComparison();

    if (CandleDirection.DESCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);
    final var secondComparison = secondCandle.getComparison();

    if (CandleDirection.ASCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    if (firstCandle.getLowerWickSize()
      .compareTo(secondCandle.getLowerWickSize()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getUpperWickSize()
      .compareTo(secondCandle.getUpperWickSize()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getBodySize()
      .compareTo(secondCandle.getBodySize()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getClose()
      .compareTo(secondCandle.getOpen()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getOpen()
      .compareTo(secondCandle.getClose()) < 0) {
      return Optional.empty();
    }

    if (firstCandle.getHigh()
      .compareTo(secondCandle.getHigh()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getLow()
      .compareTo(secondCandle.getLow()) < 0) {
      return Optional.empty();
    }

    return Optional.of(firstCandle);
  }
}
