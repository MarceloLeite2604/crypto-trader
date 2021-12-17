package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandlePosition;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleSizeCategory;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleType;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CandleAnalyser {

  public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

  public static final BigDecimal CATEGORIES_THRESHOLD = BigDecimal.valueOf(0.05);

  public static final BigDecimal TWO = BigDecimal.valueOf(2);

  private final ValidationUtil validationUtil;

  public List<CandleAnalysis> analyse(List<CandleAnalysis> candlesAnalysis) {

    final List<CandleAnalysis> alreadyAnalysedCandles = candlesAnalysis.stream()
      .filter(CandleAnalysis::isDone)
      .collect(Collectors.toCollection(ArrayList::new));

    List<CandleAnalysis> newCandleAnalysis = candlesAnalysis.stream()
      .filter(candleAnalysis -> !candleAnalysis.isDone())
      .map(this::analyse)
      .collect(Collectors.toCollection(ArrayList::new));

    newCandleAnalysis = fillCandleSizeCategories(newCandleAnalysis);
    newCandleAnalysis = fillBodySizeCategories(newCandleAnalysis);
    newCandleAnalysis = fillPosition(newCandleAnalysis);

    alreadyAnalysedCandles.addAll(newCandleAnalysis);
    alreadyAnalysedCandles.sort(Collections.reverseOrder());
    return alreadyAnalysedCandles;
  }

  private List<CandleAnalysis> fillCandleSizeCategories(List<CandleAnalysis> candlesAnalysis) {

    final var averageSize = calculateAverage(candlesAnalysis, CandleAnalysis::getSize);

    final var updatedCandlesAnalysis = new ArrayList<CandleAnalysis>(candlesAnalysis.size());

    for (CandleAnalysis candleAnalysis : candlesAnalysis) {

      final var ratio = candleAnalysis.getSize()
        .divide(averageSize, DEFAULT_ROUNDING_MODE);

      final var step = BigDecimal.valueOf(1.0 / (CandleSizeCategory.values().length / 2.0));
      var index = ratio.divide(step, DEFAULT_ROUNDING_MODE);

      if (index.intValue() >= CandleSizeCategory.values().length) {
        index = BigDecimal.valueOf(CandleSizeCategory.values().length - 1);
      }

      final var candleSizeCategory = CandleSizeCategory.values()[index.intValue()];

      final var updatedCandleAnalysis = new CandleAnalysis(candleAnalysis);
      updatedCandleAnalysis.setCandleSizeCategory(candleSizeCategory);
      updatedCandlesAnalysis.add(updatedCandleAnalysis);
    }

    return updatedCandlesAnalysis;
  }

  private List<CandleAnalysis> fillBodySizeCategories(List<CandleAnalysis> candlesAnalysis) {

    final var averageBodySize = calculateAverage(candlesAnalysis, CandleAnalysis::getBodySize);

    final var updatedCandlesAnalysis = new ArrayList<CandleAnalysis>(candlesAnalysis.size());

    for (CandleAnalysis candleAnalysis : candlesAnalysis) {

      final var ratio = candleAnalysis.getBodySize()
        .divide(averageBodySize, DEFAULT_ROUNDING_MODE);

      final var step = BigDecimal.valueOf(1.0 / (CandleSizeCategory.values().length / 2.0));
      var index = ratio.divide(step, DEFAULT_ROUNDING_MODE);

      if (index.intValue() >= CandleSizeCategory.values().length) {
        index = BigDecimal.valueOf(CandleSizeCategory.values().length - 1);
      }

      final var bodySizeCategory = CandleSizeCategory.values()[index.intValue()];

      final var updatedCandleAnalysis = new CandleAnalysis(candleAnalysis);
      updatedCandleAnalysis.setBodySizeCategory(bodySizeCategory);
      updatedCandlesAnalysis.add(updatedCandleAnalysis);
    }

    return updatedCandlesAnalysis;
  }

  private BigDecimal calculateAverage(List<CandleAnalysis> candlesAnalyses, Function<CandleAnalysis, BigDecimal> getter) {
    return candlesAnalyses.stream()
      .map(getter)
      .mapToDouble(BigDecimal::doubleValue)
      .average()
      .stream()
      .mapToObj(BigDecimal::valueOf)
      .findFirst()
      .orElse(BigDecimal.ZERO);
  }

  private CandleAnalysis analyse(CandleAnalysis candleAnalysis) {
    Assert.notNull(candleAnalysis, "Candle analysis cannot be null.");
    validationUtil.throwIllegalArgumentExceptionIfNotValid(candleAnalysis, "Cannot analyze candle.");

    final var candle = candleAnalysis.getCandle();

    if (candle.getHigh() == null || candle.getLow() == null || candle.getOpen() == null || candle.getClose() == null) {
      return CandleAnalysis.builder()
        .candle(candle)
        .direction(CandleDirection.STALLED)
        .type(CandleType.NO_OPERATION)
        .build();
    }

    final var size = candle.getHigh()
      .subtract(candle.getLow());

    final var average = candle.getLow()
      .add(size.divide(TWO, DEFAULT_ROUNDING_MODE));

    BigDecimal bodyTop;
    BigDecimal bodyBottom;
    CandleDirection direction;
    if (candle.getOpen()
      .compareTo(candle.getClose()) > 0) {
      bodyTop = candle.getOpen();
      bodyBottom = candle.getClose();
      direction = CandleDirection.DESCENDING;
    } else if (candle.getOpen()
      .compareTo(candle.getClose()) < 0) {
      bodyTop = candle.getClose();
      bodyBottom = candle.getOpen();
      direction = CandleDirection.ASCENDING;
    } else {
      bodyTop = bodyBottom = candle.getOpen();
      direction = CandleDirection.STALLED;
    }

    final var upperWickSize = candle.getHigh()
      .subtract(bodyTop);

    final var lowerWickSize = bodyBottom.subtract(candle.getLow());

    final var bodySize = bodyTop.subtract(bodyBottom);

    final var bodyAverage = bodyBottom.add(bodySize.divide(TWO, DEFAULT_ROUNDING_MODE));

    final var upperWickPercentage = size.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : upperWickSize.divide(size, DEFAULT_ROUNDING_MODE);

    final var lowerWickPercentage = size.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : lowerWickSize.divide(size, DEFAULT_ROUNDING_MODE);

    final var bodyPercentage = size.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : bodySize.divide(size, DEFAULT_ROUNDING_MODE);

    final boolean bodyPresent = bodyPercentage.compareTo(CATEGORIES_THRESHOLD) > 0;

    final boolean upperWickPresent = upperWickPercentage.compareTo(CATEGORIES_THRESHOLD) > 0;

    final boolean lowerWickPresent = lowerWickPercentage.compareTo(CATEGORIES_THRESHOLD) > 0;

    final boolean ascending = CandleDirection.ASCENDING.equals(direction);


    final var type = CandleType.find(upperWickPresent, lowerWickPresent, bodyPresent, ascending);

    return CandleAnalysis.builder()
      .candle(candle)
      .size(size)
      .bodySize(bodySize)
      .upperWickSize(upperWickSize)
      .lowerWickSize(lowerWickSize)
      .upperWickPercentage(upperWickPercentage)
      .lowerWickPercentage(lowerWickPercentage)
      .bodyPercentage(bodyPercentage)
      .direction(direction)
      .type(type)
      .average(average)
      .bodyAverage(bodyAverage)
      .done(true)
      .build();
  }

  private List<CandleAnalysis> fillPosition(List<CandleAnalysis> candlesAnalysis) {
    CandleAnalysis previous = null;
    CandleAnalysis current;
    final var updatedCandlesAnalysis = new ArrayList<CandleAnalysis>(candlesAnalysis.size());

    Collections.sort(candlesAnalysis);

    for (CandleAnalysis candleAnalysis : candlesAnalysis) {
      current = new CandleAnalysis(candleAnalysis);

      if (previous == null) {
        if (current.getPosition() == null) {
          current.setPosition(CandlePosition.SAME_LEVEL);
        }

      } else {
        if (candleAnalysis.getAverage()
          .compareTo(previous.getAverage()) > 0) {
          current.setPosition(CandlePosition.RAISED);
        } else if (candleAnalysis.getAverage()
          .compareTo(previous.getAverage()) < 0) {
          current.setPosition(CandlePosition.LOWERED);
        } else {
          current.setPosition(CandlePosition.SAME_LEVEL);
        }
      }

      updatedCandlesAnalysis.add(current);
      previous = current;
    }

    updatedCandlesAnalysis.sort(Collections.reverseOrder());
    return updatedCandlesAnalysis;
  }

}
