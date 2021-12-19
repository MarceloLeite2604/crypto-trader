package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleType;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCandleResponsePayloadToListCandleMapper
  implements Mapper<GetCandleResponsePayload, List<Candle>> {

  public static final BigDecimal TWO = BigDecimal.valueOf(2);

  private final String symbol;

  private final CandlePrecision precision;

  private final DateTimeUtil dateTimeUtil;

  @Override
  public List<Candle> mapTo(GetCandleResponsePayload getCandleResponsePayload) {
    if (CollectionUtils.isEmpty(getCandleResponsePayload.getClose())) {
      return Collections.emptyList();
    }

    final var candles = new ArrayList<Candle>(getCandleResponsePayload.getClose()
      .size());

    for (int count = 0; count < getCandleResponsePayload.getClose()
      .size(); count++) {

      final var close = BigDecimal.valueOf(getCandleResponsePayload.getClose()
        .get(count));

      final var high = BigDecimal.valueOf(getCandleResponsePayload.getHigh()
        .get(count));

      final var low = BigDecimal.valueOf(getCandleResponsePayload.getLow()
        .get(count));

      final var open = BigDecimal.valueOf(getCandleResponsePayload.getOpen()
        .get(count));

      final var timestamp = dateTimeUtil.convertEpochToUtcOffsetDateTime(getCandleResponsePayload.getTimestamp()
        .get(count));

      final var volume = BigDecimal.valueOf(getCandleResponsePayload.getVolume()
        .get(count));

      final var partialCandle = Candle.builder()
        .close(close)
        .high(high)
        .low(low)
        .open(open)
        .precision(precision)
        .symbol(symbol)
        .timestamp(timestamp)
        .volume(volume)
        .build();

      final var candle = analyse(partialCandle);

      candles.add(candle);
    }

    return candles;
  }

  private Candle analyse(Candle partialCandle) {


    final var size = partialCandle.getHigh()
      .subtract(partialCandle.getLow());

    final var average = partialCandle.getLow()
      .add(size.divide(TWO, GeneralConfiguration.DEFAULT_ROUNDING_MODE));

    BigDecimal bodyTop;
    BigDecimal bodyBottom;
    CandleDirection direction;
    if (partialCandle.getOpen()
      .compareTo(partialCandle.getClose()) > 0) {
      bodyTop = partialCandle.getOpen();
      bodyBottom = partialCandle.getClose();
      direction = CandleDirection.DESCENDING;
    } else if (partialCandle.getOpen()
      .compareTo(partialCandle.getClose()) < 0) {
      bodyTop = partialCandle.getClose();
      bodyBottom = partialCandle.getOpen();
      direction = CandleDirection.ASCENDING;
    } else {
      bodyTop = bodyBottom = partialCandle.getOpen();
      direction = CandleDirection.STALLED;
    }

    final var upperWickSize = partialCandle.getHigh()
      .subtract(bodyTop);

    final var lowerWickSize = bodyBottom.subtract(partialCandle.getLow());

    final var bodySize = bodyTop.subtract(bodyBottom);

    final var bodyAverage = bodyBottom.add(bodySize.divide(TWO, GeneralConfiguration.DEFAULT_ROUNDING_MODE));

    final var upperWickPercentage = size.compareTo(BigDecimal.ZERO) == 0 ?
      BigDecimal.ZERO : upperWickSize.divide(size, GeneralConfiguration.DEFAULT_ROUNDING_MODE);

    final var lowerWickPercentage = size.compareTo(BigDecimal.ZERO) == 0 ?
      BigDecimal.ZERO : lowerWickSize.divide(size, GeneralConfiguration.DEFAULT_ROUNDING_MODE);

    final var bodyPercentage = size.compareTo(BigDecimal.ZERO) == 0 ?
      BigDecimal.ZERO : bodySize.divide(size, GeneralConfiguration.DEFAULT_ROUNDING_MODE);

    final CandleType type = retrieveType(direction, upperWickPercentage, lowerWickPercentage, bodyPercentage);

    return Candle.builder()
      .close(partialCandle.getClose())
      .high(partialCandle.getHigh())
      .low(partialCandle.getLow())
      .open(partialCandle.getOpen())
      .precision(precision)
      .symbol(symbol)
      .timestamp(partialCandle.getTimestamp())
      .volume(partialCandle.getVolume())
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
      .build();
  }

  private CandleType retrieveType(
    CandleDirection direction,
    BigDecimal upperWickPercentage,
    BigDecimal lowerWickPercentage,
    BigDecimal bodyPercentage) {
    final boolean bodyPresent = bodyPercentage.compareTo(GeneralConfiguration.COMPARISON_THRESHOLD) > 0;

    final boolean upperWickPresent = upperWickPercentage.compareTo(GeneralConfiguration.COMPARISON_THRESHOLD) > 0;

    final boolean lowerWickPresent = lowerWickPercentage.compareTo(GeneralConfiguration.COMPARISON_THRESHOLD) > 0;

    final boolean ascending = CandleDirection.ASCENDING.equals(direction);

    return CandleType.find(upperWickPresent, lowerWickPresent, bodyPresent, ascending);
  }
}
