package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles;

import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class CandlesRequestFixture {

  public static final OffsetDateTime TO_TIME = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 25, 15, 42, 59),
    ZoneOffset.UTC);

  public static final OffsetDateTime FROM = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 25, 15, 30, 16),
    ZoneOffset.UTC);

  public static final int TO_COUNT = 236732;
  public static final int COUNTBACK = 5289712;

  public static CandlesRequest createWithTime() {
    return CandlesRequest.builder()
      .active(ActiveFixture.retrieve())
      .toTime(TO_TIME)
      .from(FROM)
      .resolution(CandleFixture.PRECISION)
      .build();
  }

  public static CandlesRequest createWithCountback() {
    return CandlesRequest.builder()
      .active(ActiveFixture.retrieve())
      .toCount(TO_COUNT)
      .countback(COUNTBACK)
      .resolution(CandleFixture.PRECISION)
      .build();
  }
}
