package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleProportion;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternType;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BearishEngulfingPatternChecker extends AbstractPatternChecker {

  private static final List<CandleProportion> VALID_BODY_SIZE_CATEGORIES = List.of(
    CandleProportion.MEDIUM,
    CandleProportion.MEDIUM_LARGE,
    CandleProportion.LARGE,
    CandleProportion.VERY_LARGE,
    CandleProportion.ENORMOUS);

  public BearishEngulfingPatternChecker() {
    super(PatternType.BEARISH_ENGULFING, 2);
  }

  @Override
  public Optional<Candle> findMatch(PatternCheckContext patternCheckContext) {

    final var candles = patternCheckContext.getCandles();

    final var firstCandle = candles.get(0);
    final var firstComparison = firstCandle.getComparison();

    if (!CandleDirection.DESCENDING.equals(firstCandle.getDirection())) {
      return Optional.empty();
    }

    final var secondCandle = candles.get(1);
    final var secondComparison = secondCandle.getComparison();

    if (!CandleDirection.ASCENDING.equals(secondCandle.getDirection())) {
      return Optional.empty();
    }

    if (firstCandle.getLowerWickSize()
      .compareTo(secondCandle.getLowerWickSize()) > 0) {
      return Optional.empty();
    }

    if (firstCandle.getUpperWickSize()
      .compareTo(secondCandle.getUpperWickSize()) < 0) {
      return Optional.empty();
    }

    if (!VALID_BODY_SIZE_CATEGORIES.contains(firstComparison.getBodyProportion())) {
      return Optional.empty();
    }

    if (!VALID_BODY_SIZE_CATEGORIES.contains(secondComparison.getBodyProportion())) {
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

    if (secondCandle.getBodySize()
      .compareTo(firstCandle.getBodySize()) > 0) {
      return Optional.empty();
    }

    if (secondCandle.getSize()
      .compareTo(firstCandle.getSize()) > 0) {
      return Optional.empty();
    }

    if (secondCandle.getOpen()
      .compareTo(firstCandle.getClose()) < 0) {
      return Optional.empty();
    }

    if (secondCandle.getClose()
      .compareTo(firstCandle.getOpen()) > 0) {
      return Optional.empty();
    }

    if (secondCandle.getHigh()
      .compareTo(firstCandle.getHigh()) > 0) {
      return Optional.empty();
    }

    if (secondCandle.getLow()
      .compareTo(firstCandle.getLow()) < 0) {
      return Optional.empty();
    }

    log.debug("First candle: {}", firstCandle);
    log.debug("Second candle: {}", secondCandle);
    return Optional.of(firstCandle);
  }
}
