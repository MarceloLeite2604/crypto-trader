package com.github.marceloleite2604.cryptotrader.test.util.fixture.candle;

import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GetCandleResponsePayloadFixture {

  public static final double CLOSE_VALUE = 12432564d;
  public static final double HIGH_VALUE = 43823d;
  public static final double LOW_VALUE = 12058.21959d;
  public static final double OPEN_VALUE = 125.7272d;
  public static final double VOLUME_VALUE = 123264378.25d;
  public static final long TIMESTAMP_VALUE = 1642713076L;

  public static final List<Double> CLOSE = List.of(CLOSE_VALUE);
  public static final List<Double> HIGH = List.of(HIGH_VALUE);
  public static final List<Double> LOW = List.of(LOW_VALUE);
  public static final List<Double> OPEN = List.of(OPEN_VALUE);
  public static final List<Double> VOLUME = List.of(VOLUME_VALUE);
  public static final List<Long> TIMESTAMP = List.of(TIMESTAMP_VALUE);

  public static GetCandleResponsePayload create() {
    return GetCandleResponsePayload.builder()
      .close(CLOSE)
      .high(HIGH)
      .low(LOW)
      .open(OPEN)
      .volume(VOLUME)
      .timestamp(TIMESTAMP)
      .build();
  }
}
