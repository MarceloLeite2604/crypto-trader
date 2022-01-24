package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.Ticker;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.tickers.TickerDtoFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class TickerFixture {

  public static final OffsetDateTime DATE = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 21, 2, 1, 25),
    ZoneOffset.UTC);

  public static Ticker create() {
    return Ticker.builder()
      .buy(new BigDecimal(TickerDtoFixture.BUY))
      .high(new BigDecimal(TickerDtoFixture.HIGH))
      .last(new BigDecimal(TickerDtoFixture.LAST))
      .low(new BigDecimal(TickerDtoFixture.LOW))
      .open(new BigDecimal(TickerDtoFixture.OPEN))
      .pair(TickerDtoFixture.PAIR)
      .sell(new BigDecimal(TickerDtoFixture.SELL))
      .volume(new BigDecimal(TickerDtoFixture.VOL))
      .date(DATE)
      .build();
  }
}
