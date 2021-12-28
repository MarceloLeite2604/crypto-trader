package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class BullishEngulfingPatternChecker extends AbstractPatternChecker {

  public BullishEngulfingPatternChecker() {
    super(PatternType.BULLISH_ENGULFING, 2);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);

    if (!CandleDirection.DESCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);

    if (!CandleDirection.ASCENDING.equals(secondCandle.getDirection())) {
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
      .compareTo(BigDecimal.ZERO) == 0) {
      return Optional.empty();
    }

    if (secondCandle.getBodySize()
      .compareTo(BigDecimal.ZERO) == 0) {
      return Optional.empty();
    }

    if (firstCandle.getBodySize()
      .compareTo(secondCandle.getBodySize()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getOpen()
      .compareTo(secondCandle.getClose()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getClose()
      .compareTo(secondCandle.getOpen()) < 0) {
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

    log.debug("First candle: {}", firstCandle);
    log.debug("Second candle: {}", secondCandle);

    return Optional.of(firstCandle);
  }
}
