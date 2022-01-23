package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleDirection;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleType;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.candle.GetCandleResponsePayloadFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class CandleFixture {

  public static final OffsetDateTime TIMESTAMP = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 20, 21, 11, 16),
    ZoneOffset.UTC);
  public static final BigDecimal OPEN = BigDecimal.valueOf(GetCandleResponsePayloadFixture.OPEN_VALUE);
  public static final BigDecimal HIGH = BigDecimal.valueOf(GetCandleResponsePayloadFixture.HIGH_VALUE);
  public static final BigDecimal LOW = BigDecimal.valueOf(GetCandleResponsePayloadFixture.LOW_VALUE);
  public static final BigDecimal CLOSE = BigDecimal.valueOf(GetCandleResponsePayloadFixture.CLOSE_VALUE);
  public static final CandlePrecision PRECISION = CandlePrecision.ONE_MONTH;
  public static final BigDecimal VOLUME = BigDecimal.valueOf(GetCandleResponsePayloadFixture.VOLUME_VALUE);
  public static final BigDecimal SIZE = BigDecimal.valueOf(12876.214596d);
  public static final BigDecimal BODY_SIZE = BigDecimal.valueOf(21874.1226d);
  public static final BigDecimal UPPER_WICK_SIZE = BigDecimal.valueOf(7092.249d);
  public static final BigDecimal LOWER_WICK_SIZE = BigDecimal.valueOf(295.452d);
  public static final BigDecimal BODY_PERCENTAGE = BigDecimal.valueOf(41848.14673d);
  public static final BigDecimal AVERAGE = BigDecimal.valueOf(296822251.65d);
  public static final BigDecimal BODY_AVERAGE = BigDecimal.valueOf(2134573.825007d);
  public static final CandleDirection DIRECTION = CandleDirection.DESCENDING;
  public static final CandleType COMMON = CandleType.COMMON;
  public static final String SYMBOL = "symbolValue";

  public static Candle createAnalysedAndCompared() {
    return createRaw().toBuilder()
      .size(SIZE)
      .bodySize(BODY_SIZE)
      .upperWickSize(UPPER_WICK_SIZE)
      .lowerWickSize(LOWER_WICK_SIZE)
      .bodyPercentage(BODY_PERCENTAGE)
      .average(AVERAGE)
      .bodyAverage(BODY_AVERAGE)
      .direction(DIRECTION)
      .type(COMMON)
      .comparison(CandleComparisonFixture.create())
      .build();
  }

  public static Candle createRaw() {
    return Candle.builder()
      .timestamp(TIMESTAMP)
      .open(OPEN)
      .high(HIGH)
      .low(LOW)
      .close(CLOSE)
      .precision(PRECISION)
      .symbol(SYMBOL)
      .volume(VOLUME)
      .build();
  }

}
